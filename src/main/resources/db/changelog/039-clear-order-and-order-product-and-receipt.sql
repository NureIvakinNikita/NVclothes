ALTER TABLE project.receipt DROP CONSTRAINT receipt_order_id_order_group_id_fk;
ALTER TABLE project.orders DROP CONSTRAINT fkjkfbfl49r5degi8lmpyfo1cf2;


DELETE FROM project.order_product;
DELETE FROM project.orders;
DELETE FROM project.receipt;







