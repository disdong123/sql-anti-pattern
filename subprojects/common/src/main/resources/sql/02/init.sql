DROP    DATABASE    anti_pattern;
CREATE  DATABASE    anti_pattern;

CREATE TABLE account(
                        id          INT    PRIMARY KEY ,
                        name        VARCHAR(1000)
);


CREATE TABLE product(
                        id          INT             PRIMARY KEY ,
                        name        VARCHAR(1000)
--                         account_id  INT             ,
--                         FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE contact(
                        product_id          INT     NOT NULL,
                        account_id          INT     NOT NULL,
                        PRIMARY KEY (product_id, account_id),
                        FOREIGN KEY (product_id) REFERENCES product(id),
                        FOREIGN KEY (account_id) REFERENCES account(id)
);


INSERT INTO account(id, name) VALUES (1, "한길");
INSERT INTO account(id, name) VALUES (2, "두길");
INSERT INTO account(id, name) VALUES (3, "세길");

INSERT INTO product(id, name) VALUES (1, "모자");
INSERT INTO product(id, name) VALUES (2, "비둘기");
INSERT INTO product(id, name) VALUES (3, "천사");
INSERT INTO product(id, name) VALUES (4, "요네");
INSERT INTO product(id, name) VALUES (5, "파시");

INSERT INTO contact(product_id, account_id) VALUES (1, 1);
INSERT INTO contact(product_id, account_id) VALUES (2, 2);
INSERT INTO contact(product_id, account_id) VALUES (3, 3);
INSERT INTO contact(product_id, account_id) VALUES (4, 1);
INSERT INTO contact(product_id, account_id) VALUES (5, 1);