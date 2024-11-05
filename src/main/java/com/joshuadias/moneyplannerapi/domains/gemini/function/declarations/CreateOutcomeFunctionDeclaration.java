package com.joshuadias.moneyplannerapi.domains.gemini.function.declarations;

import com.google.cloud.vertexai.api.FunctionDeclaration;
import com.google.cloud.vertexai.api.Schema;
import com.google.cloud.vertexai.api.Type;
import com.google.protobuf.Value;

import java.util.List;

public class CreateOutcomeFunctionDeclaration {

    public static final String FUNCTION_NAME = "createOutcome";

    private CreateOutcomeFunctionDeclaration() {
    }

    public static FunctionDeclaration createOutcomeFunctionDeclaration() {
        return FunctionDeclaration.newBuilder()
                .setName(FUNCTION_NAME)
                .setDescription("Creates a single outcome containing the information of the user purchase")
                .setParameters(
                        Schema.newBuilder()
                                .setType(Type.OBJECT)
                                .putProperties("description", Schema.newBuilder()
                                        .setType(Type.STRING)
                                        .setDescription(
                                                "Brief name or description of the purchase. If the purchase has multiple items, it should consider the place where it was bought")
                                        .build()
                                )
                                .putProperties("value", Schema.newBuilder()
                                        .setType(Type.NUMBER)
                                        .setDescription("Value of the purchase")
                                        .build()
                                )
                                .putProperties("date", Schema.newBuilder()
                                        .setType(Type.STRING)
                                        .setDescription(
                                                "Date when the purchase was made. It should be formatted as mm/dd/yyyy")
                                        .build()
                                )
                                .putProperties("installments", Schema.newBuilder()
                                        .setType(Type.INTEGER)
                                        .setDescription(
                                                "The quantity of installments that the purchase will be divided")
                                        .setDefault(Value.newBuilder().setNumberValue(1).build())
                                        .build()
                                )
                                .addAllRequired(List.of("description", "value", "date", "installments"))
                                .build()
                )
                .build();
    }
}
