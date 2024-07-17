alter table spending_goal
    add name varchar(50) default 'test' not null;

alter table spending_goal
    alter column name drop default;

