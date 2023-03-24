ALTER TABLE project.receipt
    ADD CONSTRAINT receipt_order_id_order_group_id_fk
        FOREIGN KEY (order_id, order_group_id)
            REFERENCES project.orders (id, order_group_id);

