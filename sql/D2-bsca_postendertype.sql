CREATE TABLE pos.bsca_postendertype (
	id varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	bsca_currency_id varchar(255) NOT NULL,
	isallowchange bool NOT NULL DEFAULT false,
	issetdifference bool NOT NULL DEFAULT false,
	classname text NULL,
	isactive bool NOT NULL DEFAULT true,
	node_id varchar(25) NULL,
	idempiere_id numeric NULL,
	amtto float8 NULL,
	amtfrom float8 NULL,
	movementType varchar(2) NULL,
	CONSTRAINT bsca_postendertype_pkey PRIMARY KEY (id),
	CONSTRAINT bsca_postendertype_fk FOREIGN KEY (bsca_currency_id) REFERENCES pos.bsca_currency(id)
);
CREATE INDEX bsca_postendertype_fk ON pos.bsca_postendertype USING btree (bsca_currency_id);