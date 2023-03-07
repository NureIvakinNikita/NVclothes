CREATE TABLE project.orders (
        id SERIAL,
        order_group_id INTEGER NOT NULL,
        registration_date DATE NOT NULL,
        product_type TEXT NOT NULL,
        address TEXT NOT NULL,
        PRIMARY KEY (id, order_group_id)
);