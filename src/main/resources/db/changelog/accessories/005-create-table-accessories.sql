CREATE TABLE accessories(
    id SERIAL NOT NULL PRIMARY KEY,
    accessories_id BIGINT NOT NULL ,
    attribute VARCHAR NOT NULL,
    value VARCHAR NOT NULL
);