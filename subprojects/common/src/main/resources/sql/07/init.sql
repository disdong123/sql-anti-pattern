create table bug(
                    id      int primary key ,

);

create table feature_request(
    id      int primary key
);

create table comment(
                        id      int primary key ,
                        author      int not null ,
);


create table bug_comment(
                            issue_id        int not null ,
                            comment_id      int not null ,
                            unique key (comment_id),
                            primary key (issue_id, comment_id),
                            foreign key (issue_id) references bug(id),
                            foreign key (comment_id) references comment(id),
);

create table feature_request_comment(
                            issue_id        int not null ,
                            comment_id      int not null ,
                            unique key (comment_id),
                            primary key (issue_id, comment_id),
                            foreign key (issue_id) references bug(id),
                            foreign key (comment_id) references comment(id),
);




select *
from bug
inner join bug_comment bc on bug.id = bc.issue_id
inner join comment c2 on c2.id = bc.comment_id


select *
from bug_comment
inner join comment c2 on c2.id = bug_comment.comment_id
where bug_comment.issue_id = 1000;