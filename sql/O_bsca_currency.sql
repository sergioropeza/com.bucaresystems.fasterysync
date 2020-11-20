CREATE TABLE pos.bsca_currency (
	id varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	isocode varchar(5) NOT NULL,
	cursymbol varchar(10) NOT NULL,
	multiplyrate float8 NOT NULL DEFAULT '1'::double precision,
	isconversioncurrency bool NOT NULL DEFAULT false,
	isactive bool NOT NULL DEFAULT true,
	node_id varchar(25) NULL,
	idempiere_id numeric NULL,
	CONSTRAINT bsca_currency_pkey PRIMARY KEY (id),
	CONSTRAINT currency_inx_0 UNIQUE (isocode),
	CONSTRAINT currency_inx_1 UNIQUE (cursymbol)
);