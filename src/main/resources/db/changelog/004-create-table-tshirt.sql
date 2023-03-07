CREATE TABLE project.t_shirt(
    id SERIAL NOT NULL PRIMARY KEY,
    t_shirt_id BIGINT NOT NULL ,
    attribute VARCHAR NOT NULL,
    value VARCHAR NOT NULL
);