DROP    DATABASE    anti_pattern;
CREATE  DATABASE    anti_pattern;

USE anti_pattern;

CREATE issue(
    id INT PRIMARY KEY
);

CREATE issue_attribute(
    issue_id        INT,
    attribute_name  varchar(100),
    attribute_value varchar(100),
    PRIMARY KEY (issue_id, attribute_name),
    FOREIGN KEY (issue_id) REFERENCES issue(id)
);

INSERT INT issue_attribute values (1234, 'created_at', '2022-10-10 01:01:01');
INSERT INT issue_attribute values (1234, 'updated_at', '2022-10-10 01:01:01');