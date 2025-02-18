ALTER TABLE catalog_broker ADD is_active BIT DEFAULT 1 NOT NULL;
ALTER TABLE catalog_issue ADD is_active BIT DEFAULT 1 NOT NULL;
ALTER TABLE catalog_type_currency ADD is_active BIT DEFAULT 1 NOT NULL;
ALTER TABLE catalog_type_movement ADD is_active BIT DEFAULT 1 NOT NULL;
ALTER TABLE catalog_type_transaction ADD is_active BIT DEFAULT 1 NOT NULL;