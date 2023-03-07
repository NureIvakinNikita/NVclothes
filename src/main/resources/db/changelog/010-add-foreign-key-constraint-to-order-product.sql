ALTER TABLE project.order_product
ADD CONSTRAINT order_product_order_id_fk
FOREIGN KEY (order_id, order_group_id)
REFERENCES project.orders (id, order_group_id);