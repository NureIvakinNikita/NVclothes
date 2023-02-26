ALTER TABLE project.client
ADD COLUMN cart_id BIGINT REFERENCES cart(id);