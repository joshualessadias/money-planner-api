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
    OUTCOME_CATEGORY_FINDING_ALL("message.outcome.category.finding.all"),
    OUTCOME_CATEGORY_FINDING_ALL_PAGEABLE("message.outcome.category.finding.all.pageable"),
    OUTCOME_CATEGORY_FOUND_ALL_PAGEABLE("message.outcome.category.found.all.pageable"),
    OUTCOME_CATEGORY_UPDATING_WITH_ID("message.outcome.category.updating.with.id"),
    OUTCOME_CATEGORY_UPDATED_WITH_ID("message.outcome.category.updated.with.id"),

    PAYMENT_METHOD_NOT_FOUND_WITH_ID("message.payment.method.not.found.with.id"),

    BANK_NOT_FOUND_WITH_ID("message.bank.not.found.with.id"),

    ORDER_BY_VALIDATION_ERROR("message.order.by.validation.error"),

    APP_USER_CREATING("message.app.user.creating"),
    APP_USER_CREATED_WITH_ID("message.app.user.created.with.id"),
    ROLE_CREATING("message.role.creating"),
    ROLE_CREATED_WITH_ID("message.role.created.with.id"),
    ADDING_ROLE_WITH_ID_TO_APP_USER_WITH_ID("message.adding.role.with.id.to.app.user.with.id"),
    APP_USER_NOT_FOUND_WITH_ID("message.app.user.not.found.with.id"),
    ROLE_NOT_FOUND_WITH_ID("message.role.not.found.with.id"),
    ROLE_ADDED_TO_APP_USER("message.role.added.to.app.user"),
    APP_USER_NOT_FOUND_WITH_EMAIL("message.app.user.not.found.with.email"),
    APP_USER_FINDING_ALL_PAGEABLE("message.app.user.finding.all.pageable"),
    APP_USER_FOUND_ALL_PAGEABLE("message.app.user.found.all.pageable"),
    APP_USER_EMAIL_ALREADY_REGISTERED("message.app.user.email.already.registered"),
    APP_USER_FINDING_CURRENT("message.app.user.finding.current"),
    APP_USER_FOUND_CURRENT_BY_EMAIL("message.app.user.found.current.by.email");

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
