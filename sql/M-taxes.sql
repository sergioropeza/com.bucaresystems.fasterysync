CREATE TABLE pos.taxes (
	id varchar NOT NULL,
	"name" varchar NOT NULL,
	category varchar NOT NULL,
	custcategory varchar NULL,
	parentid varchar NULL,
	rate float8 NOT NULL DEFAULT 0,
	ratecascade bool NOT NULL DEFAULT false,
	rateorder int4 NULL,
	idempiere_id numeric NULL,
	CONSTRAINT pk_taxes PRIMARY KEY (id),
	CONSTRAINT taxes_name_inx UNIQUE (name),
	CONSTRAINT taxes_cat_fk FOREIGN KEY (category) REFERENCES pos.taxcategories(id)
);