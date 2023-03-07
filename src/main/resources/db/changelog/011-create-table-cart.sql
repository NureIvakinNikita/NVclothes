CREATE TABLE project.cart(
    id SERIAL NOT NULL PRIMARY KEY,
    product_amount BIGINT NULL,
    price BIGINT NULL,
    client_id BIGINT REFERENCES project.client(id)
)