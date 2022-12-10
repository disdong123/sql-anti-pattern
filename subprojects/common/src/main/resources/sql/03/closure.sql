DROP    DATABASE    anti_pattern;
CREATE  DATABASE    anti_pattern;

USE anti_pattern;

CREATE TABLE account(
                        id          INT    PRIMARY KEY AUTO_INCREMENT,
                        name        VARCHAR(1000)
);

CREATE TABLE bug(
                    id          INT    PRIMARY KEY AUTO_INCREMENT,
                    name        VARCHAR(1000)
);

CREATE TABLE comment(
                        id         INT             PRIMARY KEY AUTO_INCREMENT,
                        bug_id     INT             NOT NULL ,
                        author_id     INT          NOT NULL ,
                        comment    TEXT            NOT NULL ,
                        FOREIGN KEY (bug_id)       REFERENCES bug(id),
                        FOREIGN KEY (author_id)    REFERENCES account(id)
);

CREATE TABLE tree_path(
                        id      INT     PRIMARY KEY     AUTO_INCREMENT,
                          ancestor   INT NOT NULL,
                          descendant INT NOT NULL,
--                           PRIMARY KEY (ancestor, descendant),
                          FOREIGN KEY (ancestor) REFERENCES comment(id),
                          FOREIGN KEY (descendant) REFERENCES comment(id)
);


INSERT INTO account(id, name) values (1, "한길");
INSERT INTO account(id, name) values (2, "두길");
INSERT INTO account(id, name) values (3, "세길");
INSERT INTO account(id, name) values (4, "네길");

INSERT INTO bug(id, name) values (1, "버그1");
INSERT INTO bug(id, name) values (2, "버그2");
INSERT INTO bug(id, name) values (3, "버그3");
INSERT INTO bug(id, name) values (4, "버그4");