CREATE TABLE pos.categories (
	id varchar NOT NULL,
	"name" varchar NOT NULL,
	parentid varchar NULL,
	image bytea NULL,
	texttip varchar NULL,
	catshowname bool NOT NULL DEFAULT true,
	catorder varchar NULL,
	idempiere_ID numeric NULL,
	CONSTRAINT categories_pkey PRIMARY KEY (id),
	CONSTRAINT categories_fk_1 FOREIGN KEY (parentid) REFERENCES pos.categories(id)
);
CREATE UNIQUE INDEX categories_fk_1 ON pos.categories USING btree (parentid);
CREATE UNIQUE INDEX categories_name_inx ON pos.categories USING btree (name);