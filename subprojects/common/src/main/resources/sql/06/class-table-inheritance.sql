CREATE TABLE issue(
    id              INT PRIMARY KEY ,
    reported_by     INT NOT NULL ,
    product_id      INT,
    priority        varchar,
    status          varchar,
    foreign key (reported_by) references account(id),
    foreign key (product_id) references product(id)
);

create table bug(
    issue_id        int primary key ,
    severity        varchar,
    version_affected    varchar,
    foreign key (issue_id) references issue(id)
);

create table feature_request(
    issue_id        int primary key ,
    sponsor         varchar,
    foreign key (issue_id) references issue(id)
)