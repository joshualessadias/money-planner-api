CREATE TABLE spending_goal
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    last_updated_at datetime              NULL,
    value           DECIMAL(12, 2)        NOT NULL,
    initial_date    datetime              NOT NULL,
    final_date      datetime              NOT NULL,
    CONSTRAINT pk_spending_goal PRIMARY KEY (id)
);

CREATE TABLE category_spending_goal
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    created_at          datetime              NOT NULL,
    last_updated_at     datetime              NULL,
    spending_goal_id    BIGINT                NOT NULL,
    value               DECIMAL(12, 2)        NOT NULL,
    outcome_category_id BIGINT                NOT NULL,
    is_percentual       BIT(1)                NOT NULL,
    CONSTRAINT pk_category_spending_goal PRIMARY KEY (id)
);

ALTER TABLE category_spending_goal
    ADD CONSTRAINT FK_CATEGORY_SPENDING_GOAL_ON_OUTCOME_CATEGORY FOREIGN KEY (outcome_category_id) REFERENCES outcome_category (id);

ALTER TABLE category_spending_goal
    ADD CONSTRAINT FK_CATEGORY_SPENDING_GOAL_ON_SPENDING_GOAL FOREIGN KEY (spending_goal_id) REFERENCES spending_goal (id);