DROP    DATABASE    anti_pattern;
CREATE  DATABASE    anti_pattern;

CREATE TABLE account(
                        id          INT    PRIMARY KEY ,
                        name        VARCHAR(1000)
);

CREATE TABLE bug(
                        id          INT    PRIMARY KEY ,
                        name        VARCHAR(1000)
);

CREATE TABLE comment(
                         id         INT             PRIMARY KEY ,
                         parent_id  INT,
                         bug_id     INT             NOT NULL ,
                         author_id     INT          NOT NULL ,
                         comment    TEXT            NOT NULL ,
                         FOREIGN KEY (parent_id)    REFERENCES comment(id),
                         FOREIGN KEY (bug_id)       REFERENCES bug(id),
                         FOREIGN KEY (author_id)    REFERENCES account(id)
);


INSERT INTO account(id, name) values (1, "한길");
INSERT INTO account(id, name) values (2, "두길");
INSERT INTO account(id, name) values (3, "세길");
INSERT INTO account(id, name) values (4, "네길");

INSERT INTO bug(id, name) values (1, "버그1");
INSERT INTO bug(id, name) values (2, "버그2");
INSERT INTO bug(id, name) values (3, "버그3");
INSERT INTO bug(id, name) values (4, "버그4");


INSERT INTO comment(id, parent_id, bug_id, author_id, comment) values (1, null, 1, 1, "첫번째");
INSERT INTO comment(id, parent_id, bug_id, author_id, comment) values (2, 1, 1, 1, "첫번째");
INSERT INTO comment(id, parent_id, bug_id, author_id, comment) values (3, 2, 1, 1, "첫번째");
INSERT INTO comment(id, parent_id, bug_id, author_id, comment) values (4, 1, 1, 1, "첫번째");
INSERT INTO comment(id, parent_id, bug_id, author_id, comment) values (5, 4, 1, 1, "첫번째");
INSERT INTO comment(id, parent_id, bug_id, author_id, comment) values (6, 4, 1, 1, "첫번째");
INSERT INTO comment(id, parent_id, bug_id, author_id, comment) values (7, 6, 1, 1, "첫번째");