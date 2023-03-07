CREATE TABLE project.order_client (
    order_group_id INTEGER REFERENCES project.orders(id),
    client_id INTEGER REFERENCES project.client (id),
    PRIMARY KEY (order_group_id, client_id)
);