CREATE TABLE app_user
(
    id              bigint IDENTITY (1, 1) NOT NULL,
    created_at      datetime               NOT NULL,
    last_updated_at datetime,
    name            varchar(50)            NOT NULL,
    email           varchar(50)            NOT NULL,
    password        varchar(50)            NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
)
GO

CREATE TABLE app_user_role
(
    app_user_id bigint NOT NULL,
    role_id     bigint NOT NULL
)
GO

CREATE TABLE bank
(
    id              bigint IDENTITY (1, 1) NOT NULL,
    created_at      datetime               NOT NULL,
    last_updated_at datetime,
    name            varchar(255)           NOT NULL,
    CONSTRAINT pk_bank PRIMARY KEY (id)
)
GO

CREATE TABLE outcome
(
    id                bigint IDENTITY (1, 1) NOT NULL,
    created_at        datetime               NOT NULL,
    last_updated_at   datetime,
    description       varchar(100)           NOT NULL,
    value             decimal(12, 2)         NOT NULL,
    date              datetime               NOT NULL,
    category_id       bigint,
    payment_method_id bigint,
    bank_id           bigint,
    CONSTRAINT pk_outcome PRIMARY KEY (id)
)
GO

CREATE TABLE outcome_category
(
    id              bigint IDENTITY (1, 1) NOT NULL,
    created_at      datetime               NOT NULL,
    last_updated_at datetime,
    name            varchar(50)            NOT NULL,
    description     varchar(255),
    CONSTRAINT pk_outcome_category PRIMARY KEY (id)
)
GO

CREATE TABLE payment_method
(
    id              bigint IDENTITY (1, 1) NOT NULL,
    created_at      datetime               NOT NULL,
    last_updated_at datetime,
    name            varchar(255)           NOT NULL,
    description     varchar(255),
    code            varchar(255),
    CONSTRAINT pk_payment_method PRIMARY KEY (id)
)
GO

CREATE TABLE role
(
    id              bigint IDENTITY (1, 1) NOT NULL,
    created_at      datetime               NOT NULL,
    last_updated_at datetime,
    name            varchar(20)            NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
)
GO

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_email UNIQUE (email)
GO

ALTER TABLE outcome_category
    ADD CONSTRAINT uc_outcome_category_name UNIQUE (name)
GO

ALTER TABLE outcome
    ADD CONSTRAINT FK_OUTCOME_ON_BANK FOREIGN KEY (bank_id) REFERENCES bank (id)
GO

ALTER TABLE outcome
    ADD CONSTRAINT FK_OUTCOME_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES outcome_category (id)
GO

ALTER TABLE outcome
    ADD CONSTRAINT FK_OUTCOME_ON_PAYMENT_METHOD FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
GO

ALTER TABLE app_user_role
    ADD CONSTRAINT fk_appuserol_on_app_user FOREIGN KEY (app_user_id) REFERENCES app_user (id)
GO

ALTER TABLE app_user_role
    ADD CONSTRAINT fk_appuserol_on_role FOREIGN KEY (role_id) REFERENCES role (id)
GO