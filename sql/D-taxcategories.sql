CREATE TABLE pos.taxcategories (
	id varchar NOT NULL,
	"name" varchar NOT NULL,
	idempiere_id numeric NULL,
	CONSTRAINT taxcategories_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX taxcat_name_inx ON pos.taxcategories USING btree (name);