ALTER TABLE project.orders ADD CONSTRAINT fk_orders_clients
FOREIGN KEY (client_id) REFERENCES project.client(id);