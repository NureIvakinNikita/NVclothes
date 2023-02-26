CREATE TABLE project.receipt (
   id SERIAL PRIMARY KEY NOT NULL ,
   money BIGINT NOT NULL,
   paymentDate DATE NOT NULL
);