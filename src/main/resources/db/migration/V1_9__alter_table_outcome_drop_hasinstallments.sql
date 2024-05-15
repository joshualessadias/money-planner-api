alter table dbo.outcome
drop
constraint DF__outcome__has_ins__18178C8A
go

alter table dbo.outcome
drop
column has_installments
go

