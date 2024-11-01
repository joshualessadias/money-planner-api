package com.joshuadias.moneyplannerapi.domains.gemini.config;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class GenerativeModelConfig {

    @Bean
    public GenerativeModel generativeModel(Environment env, VertexAI vertexAi) {
//        var safetySettings = Arrays.asList(
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
//                        .build(),
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
//                        .build(),
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
//                        .build(),
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
//                        .build()
//        );
        return new GenerativeModel.Builder()
                .setModelName(Objects.requireNonNull(env.getProperty("gemini.model.name")))
                .setVertexAi(vertexAi)
//                .setSafetySettings(safetySettings)
                .build();
    }
}
