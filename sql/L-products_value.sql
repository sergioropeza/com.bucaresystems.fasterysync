CREATE TABLE pos.products_value (
	id varchar(255) NOT NULL,
	product varchar(255) NOT NULL,
	value varchar(255) NOT NULL,
	isactive bool NOT NULL DEFAULT true,
	ismastervalue bool NOT NULL DEFAULT false,
	isplucode bool NOT NULL DEFAULT false,
	isdefault bool NOT NULL DEFAULT false,
	isdefaultvalue bool NOT NULL DEFAULT false,
	node_id varchar(255) NULL,
	m_product_id numeric NULL,
	ad_org_id numeric NULL,
	idempiere_id numeric NULL,
	qty numeric NOT NULL DEFAULT 1,
	CONSTRAINT products_value_pkey PRIMARY KEY (id),
	CONSTRAINT products_value_uk_0 UNIQUE (value, node_id),
	CONSTRAINT products_value_fk_1 FOREIGN KEY (product) REFERENCES pos.products(id)
);
