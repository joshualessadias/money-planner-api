package com.joshuadias.moneyplannerapi.domains.gemini.services;

import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.FunctionCall;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.joshuadias.moneyplannerapi.domains.core.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.domains.core.services.OutcomeService;
import com.joshuadias.moneyplannerapi.domains.gemini.function.declarations.CreateOutcomeFunctionDeclaration;
import com.joshuadias.moneyplannerapi.domains.gemini.mappers.CreateOutcomeMapper;
import com.joshuadias.moneyplannerapi.domains.shared.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.domains.shared.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {

    private final GenerativeModel model;
    private final ContextMessageService contextMessageService;
    private final OutcomeService outcomeService;

    private Content buildSystemInstruction() {
        var contextMessage = contextMessageService.findLast().getText();
        contextMessage = contextMessage + MessageEnum.CONTEXT_MESSAGE_CURRENT_DATE.getMessage() + " " + LocalDate.now();
        return ContentMaker.fromString(contextMessage);
    }

    private String callFunction(FunctionCall functionCall) {
        if (CreateOutcomeFunctionDeclaration.FUNCTION_NAME.equals(functionCall.getName())) {
            var argsMap = functionCall.getArgs().getFieldsMap();
            var request = CreateOutcomeMapper.toRequest(argsMap);
            var createdOutcome = outcomeService.create(request);
            return "Created Outcome: " + createdOutcome.getDescription();
        }
        throw new BadRequestException(MessageEnum.FUNCTION_CALL_NOT_IMPLEMENTED.getMessage(functionCall.getName()));
    }

    private String handleResponse(GenerateContentResponse response) {
        if (ResponseHandler.getFinishReason(response) == Candidate.FinishReason.STOP
                && !ResponseHandler.getFunctionCalls(response).isEmpty()) {
            int functionCallsSize = ResponseHandler.getFunctionCalls(response).size();
            log.info("{} function calls detected", functionCallsSize);
            var functionCallResponse = new ArrayList<String>();
            while (functionCallsSize > 0) {
                functionCallResponse.add(callFunction(ResponseHandler.getFunctionCalls(response)
                                                              .get(functionCallsSize - 1)));
                functionCallsSize--;
            }
            return String.join("\n", functionCallResponse);
        }
        return ResponseHandler.getText(response);
    }

    public String sendMessage(String message) {
        log.info("Sending message to gemini: {}", message);
        GenerateContentResponse response;
        try {
            var chat = model.startChat().withSystemInstruction(buildSystemInstruction());
            response = chat.sendMessage(message);
        } catch (IOException e) {
            throw new InternalServerException(e.getMessage());
        }
        return handleResponse(response);
    }
}
