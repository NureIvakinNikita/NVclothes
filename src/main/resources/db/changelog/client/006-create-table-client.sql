CREATE TABLE client (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   lastname TEXT NOT NULL,
   email TEXT NOT NULL,
   telephone_number TEXT NOT NULL,
   birthday DATE NOT NULL,
   password TEXT NOT NULL
);