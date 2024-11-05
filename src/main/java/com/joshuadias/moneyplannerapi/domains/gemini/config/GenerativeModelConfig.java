package com.joshuadias.moneyplannerapi.domains.gemini.config;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.api.Tool;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.joshuadias.moneyplannerapi.domains.gemini.function.declarations.CreateOutcomeFunctionDeclaration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Objects;

@Configuration
public class GenerativeModelConfig {

    private static List<Tool> buildFunctionDeclarations() {
        return List.of(Tool.newBuilder().addFunctionDeclarations(
                CreateOutcomeFunctionDeclaration.createOutcomeFunctionDeclaration()
        ).build());
    }

    private static List<SafetySetting> buildSafetySettings() {
        return List.of(
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                        .build()
        );
    }

    @Bean
    public GenerativeModel generativeModel(Environment env, VertexAI vertexAi) {
        return new GenerativeModel.Builder()
                .setModelName(Objects.requireNonNull(env.getProperty("gemini.model.name")))
                .setVertexAi(vertexAi)
                .setTools(buildFunctionDeclarations())
                .setSafetySettings(buildSafetySettings())
                .build();
    }
}
