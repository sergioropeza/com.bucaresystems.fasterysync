CREATE TABLE taxcategories (
	id varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	idempiere_id numeric NULL,
	CONSTRAINT taxcategories_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX taxcat_name_inx ON taxcategories USING btree (name);