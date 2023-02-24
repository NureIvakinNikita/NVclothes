ALTER TABLE order_product
ALTER COLUMN order_product_id SET DEFAULT nextval('hibernate_sequence');