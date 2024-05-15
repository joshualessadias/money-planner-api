alter table dbo.outcome
    add has_installments bit default 0 not null
go

update dbo.outcome
set has_installments = 1
where installments > 1
go

alter table dbo.outcome
    drop constraint DF__outcome__install__17236851
go

alter table dbo.outcome
    drop column installments
go

