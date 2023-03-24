ALTER TABLE  project.receipt
ADD COLUMN order_id BIGINT NOT NULL;
ALTER TABLE project.receipt
ADD COLUMN order_group_id BIGINT NOT NULL;

