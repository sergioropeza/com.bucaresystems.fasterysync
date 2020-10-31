CREATE TABLE taxes (
	id varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	category varchar(255) NOT NULL,
	custcategory varchar(255) NULL DEFAULT NULL::character varying,
	parentid varchar(255) NULL DEFAULT NULL::character varying,
	rate float8 NOT NULL DEFAULT '0'::double precision,
	ratecascade bool NOT NULL DEFAULT false,
	rateorder int4 NULL,
	idempiere_id numeric NULL,
	CONSTRAINT pk_taxes PRIMARY KEY (id),
	CONSTRAINT taxes_name_inx UNIQUE (name),
	CONSTRAINT taxes_cat_fk FOREIGN KEY (category) REFERENCES taxcategories(id)
);