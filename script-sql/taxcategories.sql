CREATE TABLE pos.taxcategories (
	id varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	idempiere_id numeric NULL,
	CONSTRAINT taxcategories_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX taxcat_name_inx ON pos.taxcategories USING btree (name);