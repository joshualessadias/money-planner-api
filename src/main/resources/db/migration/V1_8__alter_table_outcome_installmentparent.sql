alter table dbo.outcome
    add installment_parent_id bigint
    go

alter table outcome
    add constraint FK_OUTCOME_ON_INSTALLMENT_PARENT foreign key (installment_parent_id) references outcome (id)
    go