package com.joshuadias.moneyplannerapi.enums;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@Getter
public enum MessageEnum {
    OUTCOME_CREATING("message.outcome.creating"),
    OUTCOME_UPDATING_WITH_ID("message.outcome.updating.with.id"),
    OUTCOME_CREATED_WITH_ID("message.outcome.created.with.id"),
    OUTCOME_NOT_FOUND_WITH_ID("message.outcome.not.found.with.id"),
    OUTCOME_UPDATED_WITH_ID("message.outcome.updated.with.id"),
    OUTCOME_FINDING_BY_ID("message.outcome.finding.by.id"),
    OUTCOME_FINDING_ALL_PAGEABLE("message.outcome.finding.all.pageable"),
    OUTCOME_FOUND_ALL_PAGEABLE("message.outcome.found.all.pageable"),
    OUTCOME_DELETING_WITH_ID("message.outcome.deleting.with.id"),
    OUTCOME_DELETED_WITH_ID("message.outcome.deleted.with.id"),

    OUTCOME_CATEGORY_NOT_FOUND_WITH_ID("message.outcome.category.not.found.with.id"),
    OUTCOME_CATEGORY_CREATING("message.outcome.category.creating"),
    OUTCOME_CATEGORY_CREATED_WITH_ID("message.outcome.category.created.with.id"),
    OUTCOME_CATEGORY_ALREADY_EXISTS_WITH_NAME("message.outcome.category.already.exists.with.name"),
    OUTCOME_CATEGORY_FINDING_BY_ID("message.outcome.category.finding.by.id"),

    PAYMENT_METHOD_NOT_FOUND_WITH_ID("message.payment.method.not.found.with.id"),

    BANK_NOT_FOUND_WITH_ID("message.bank.not.found.with.id");

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private final String description;

    MessageEnum(String description) {
        this.description = description;
    }

    private static String convertToUTF8(String message) {
        try {
            return new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return message;
        }
    }

    public String getMessage(String... args) {
        String msg = convertToUTF8(resourceBundle.getString(this.description));
        if (msg.contains("�")) {
            msg = resourceBundle.getString(this.description);
        }
        return args == null ? msg : MessageFormat.format(msg, (Object[]) args);
    }
}
