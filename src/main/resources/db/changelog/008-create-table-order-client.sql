CREATE TABLE project.order_client (
    order_client_id SERIAL PRIMARY KEY,
    order_group_id INTEGER,
    client_id INTEGER REFERENCES project.client(id)
);