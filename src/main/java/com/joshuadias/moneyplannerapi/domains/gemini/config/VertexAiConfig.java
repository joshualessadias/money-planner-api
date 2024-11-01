package com.joshuadias.moneyplannerapi.domains.gemini.config;

import com.google.cloud.vertexai.VertexAI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class VertexAiConfig {

    @Bean
    public VertexAI vertexAi(Environment env) {
        return new VertexAI(env.getProperty("vertex-ai.project.name"), "us-central1");
    }
}
