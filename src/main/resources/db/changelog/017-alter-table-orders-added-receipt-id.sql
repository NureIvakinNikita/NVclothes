ALTER TABLE project.orders
ADD COLUMN receipt_id BIGINT REFERENCES project.receipt(id) NULL;