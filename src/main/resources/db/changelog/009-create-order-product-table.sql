CREATE TABLE nvclothes.project.order_product (
    order_product_id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES project.orders(id),
    product_id INTEGER NOT NULL,
    order_group_id INTEGER,
    product_type TEXT NOT NULL
--     PRIMARY KEY (order_id, order_group_id, product_id)
);
/*CREATE TABLE project.order_product (
    order_product_id INTEGER NOT NULL,
    order_id INTEGER REFERENCES project.orders (id),
    product_id INTEGER,
    order_group_id INTEGER NOT NULL,
    product_type TEXT NOT NULL,
    PRIMARY KEY (order_id)
);*/