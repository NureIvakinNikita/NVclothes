CREATE TABLE order_product (
    order_product_id INTEGER NOT NULL,
    order_id INTEGER REFERENCES orders (id),
    product_id INTEGER,
    order_group_id INTEGER NOT NULL,
    product_type TEXT NOT NULL,
    PRIMARY KEY (order_id)
);