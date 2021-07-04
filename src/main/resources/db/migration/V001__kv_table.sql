CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE t_key_value
(
    namespace   VARCHAR   NOT NULL,
    key         VARCHAR   NOT NULL,
    value       VARCHAR   NOT NULL,
    update_time TIMESTAMP NOT NULL,
    PRIMARY KEY (namespace, key)
);


CREATE TABLE t_user
(
    id       BIGINT  NOT NULL PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    role     VARCHAR NOT NULL
);
