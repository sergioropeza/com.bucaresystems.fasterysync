CREATE TABLE categories (
	id varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	parentid varchar(255) NULL DEFAULT NULL::character varying,
	image bytea NULL,
	texttip varchar(255) NULL DEFAULT NULL::character varying,
	catshowname int2 NOT NULL DEFAULT '1'::smallint,
	catorder varchar(255) NULL DEFAULT NULL::character varying,
	idempiere_ID numeric NULL,
	CONSTRAINT categories_pkey PRIMARY KEY (id),
	CONSTRAINT categories_fk_1 FOREIGN KEY (parentid) REFERENCES categories(id)
);
CREATE UNIQUE INDEX categories_fk_1 ON categories USING btree (parentid);
CREATE UNIQUE INDEX categories_name_inx ON categories USING btree (name);