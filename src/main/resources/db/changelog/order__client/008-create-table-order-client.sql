CREATE TABLE project.order_client (
    order_id INTEGER REFERENCES orders (id),
    client_id INTEGER REFERENCES client (id),
    order_group_id INTEGER NOT NULL,
    PRIMARY KEY (order_id, client_id)
);