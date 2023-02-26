ALTER TABLE project.order_client
ADD CONSTRAINT order_id_foreign_key FOREIGN KEY (order_id) REFERENCES orders (id),
ADD CONSTRAINT client_id_foreign_key FOREIGN KEY (client_id) REFERENCES client(id);