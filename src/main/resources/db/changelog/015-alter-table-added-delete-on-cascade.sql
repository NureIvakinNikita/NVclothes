ALTER TABLE project.cart
ADD CONSTRAINT client_id FOREIGN KEY (client_id) REFERENCES project.client (id) ON DELETE CASCADE;