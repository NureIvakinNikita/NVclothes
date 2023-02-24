CREATE TABLE orders (
        id SERIAL PRIMARY KEY,
        order_group_id INTEGER NOT NULL,
        registration_date DATE NOT NULL,
        product_type TEXT NOT NULL,
        address TEXT NOT NULL
);