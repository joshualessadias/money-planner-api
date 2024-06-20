CREATE TABLE app_role
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    last_updated_at datetime              NULL,
    name            VARCHAR(20)           NOT NULL,
    CONSTRAINT pk_app_role PRIMARY KEY (id)
);

CREATE TABLE app_user
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    last_updated_at datetime              NULL,
    first_name      VARCHAR(50)           NOT NULL,
    last_name       VARCHAR(50)           NOT NULL,
    email           VARCHAR(50)           NOT NULL,
    password        VARCHAR(100)          NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE app_user_role
(
    app_user_id BIGINT NOT NULL,
    role_id     BIGINT NOT NULL
);

CREATE TABLE bank
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    last_updated_at datetime              NULL,
    name            VARCHAR(100)          NOT NULL,
    code            VARCHAR(10)           NOT NULL,
    CONSTRAINT pk_bank PRIMARY KEY (id)
);

CREATE TABLE outcome
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    created_at            datetime              NOT NULL,
    last_updated_at       datetime              NULL,
    `description`         VARCHAR(100)          NOT NULL,
    value                 DECIMAL(12, 2)        NOT NULL,
    date                  datetime              NOT NULL,
    category_id           BIGINT                NULL,
    payment_method_id     BIGINT                NULL,
    bank_id               BIGINT                NULL,
    installment_parent_id BIGINT                NULL,
    CONSTRAINT pk_outcome PRIMARY KEY (id)
);

CREATE TABLE outcome_category
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    last_updated_at datetime              NULL,
    name            VARCHAR(50)           NOT NULL,
    `description`   VARCHAR(255)          NULL,
    CONSTRAINT pk_outcome_category PRIMARY KEY (id)
);

CREATE TABLE payment_method
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    last_updated_at datetime              NULL,
    name            VARCHAR(100)          NOT NULL,
    `description`   VARCHAR(255)          NULL,
    code            VARCHAR(10)           NOT NULL,
    CONSTRAINT pk_payment_method PRIMARY KEY (id)
);

ALTER TABLE app_role
    ADD CONSTRAINT uc_app_role_name UNIQUE (name);

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_email UNIQUE (email);

ALTER TABLE bank
    ADD CONSTRAINT uc_bank_code UNIQUE (code);

ALTER TABLE bank
    ADD CONSTRAINT uc_bank_name UNIQUE (name);

ALTER TABLE outcome_category
    ADD CONSTRAINT uc_outcome_category_name UNIQUE (name);

ALTER TABLE payment_method
    ADD CONSTRAINT uc_payment_method_code UNIQUE (code);

ALTER TABLE payment_method
    ADD CONSTRAINT uc_payment_method_name UNIQUE (name);

ALTER TABLE outcome
    ADD CONSTRAINT FK_OUTCOME_ON_BANK FOREIGN KEY (bank_id) REFERENCES bank (id);

ALTER TABLE outcome
    ADD CONSTRAINT FK_OUTCOME_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES outcome_category (id);

ALTER TABLE outcome
    ADD CONSTRAINT FK_OUTCOME_ON_INSTALLMENT_PARENT FOREIGN KEY (installment_parent_id) REFERENCES outcome (id);

ALTER TABLE outcome
    ADD CONSTRAINT FK_OUTCOME_ON_PAYMENT_METHOD FOREIGN KEY (payment_method_id) REFERENCES payment_method (id);

ALTER TABLE app_user_role
    ADD CONSTRAINT fk_appuserol_on_app_role FOREIGN KEY (role_id) REFERENCES app_role (id);

ALTER TABLE app_user_role
    ADD CONSTRAINT fk_appuserol_on_app_user FOREIGN KEY (app_user_id) REFERENCES app_user (id);