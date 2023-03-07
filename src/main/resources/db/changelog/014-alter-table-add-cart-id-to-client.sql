ALTER TABLE project.client
ADD COLUMN cart_id BIGINT REFERENCES project.cart(id);