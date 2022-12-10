create table bug(
    id      int primary key ,

);

create table feature_request(
    id      int primary key
);

create table comment(
    id      int primary key ,
    issue_type  varchar,     -- bug 또는 featureRequest
    issue_id    int,
    author      int,
    foreign key (author) references account(id)
);



select *
from bug
         inner join comment on issue_id = comment.id
where id = 1000;


select *
from feature_request
         inner join comment on issue_id = comment.id
where id = 1000;


