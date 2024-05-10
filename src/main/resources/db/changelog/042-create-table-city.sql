CREATE TABLE project.city (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL
);

INSERT INTO project.city (name)
VALUES ('Kharkiv'), ('Kyiv'), ('City1'), ('City2'), ('City3');