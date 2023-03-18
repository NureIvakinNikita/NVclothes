CREATE TABLE project.cart_product (
        cart_product_id SERIAL PRIMARY KEY,
        cart_id BIGINT REFERENCES project.cart(id) ON DELETE CASCADE NOT NULL,
        product_id BIGINT NOT NULL,
        product_type TEXT NOT NULL
);