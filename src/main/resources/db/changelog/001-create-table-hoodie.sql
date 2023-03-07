CREATE TABLE project.hoodie(
    id SERIAL NOT NULL PRIMARY KEY,
    hoodie_id BIGINT NOT NULL ,
    attribute VARCHAR NOT NULL,
    value VARCHAR NOT NULL
);
