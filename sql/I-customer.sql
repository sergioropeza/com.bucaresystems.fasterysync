CREATE TABLE pos.customers (
	id varchar NOT NULL,
	searchkey varchar NOT NULL,
	taxid varchar NULL,
	"name" varchar NOT NULL,
	taxcategory varchar NULL,
	card varchar NULL,
	maxdebt float8 NOT NULL DEFAULT 0,
	address varchar NULL,
	address2 varchar NULL,
	postal varchar NULL,
	city varchar NULL,
	region varchar NULL,
	country varchar NULL,
	firstname varchar NULL,
	lastname varchar NULL,
	email varchar NULL,
	phone varchar NULL,
	phone2 varchar NULL,
	fax varchar NULL,
	notes varchar NULL,
	visible bool NOT NULL DEFAULT true,
	curdate timestamp NULL,
	curdebt float8 NULL DEFAULT 0,
	image bytea NULL,
	isvip bool NOT NULL DEFAULT true,
	discount float8 NULL DEFAULT 0,
	memodate timestamp NULL DEFAULT now(),
	gender varchar(255) NULL,
	CONSTRAINT customers_pkey PRIMARY KEY (id),
	CONSTRAINT customers_taxcat FOREIGN KEY (taxcategory) REFERENCES taxcustcategories(id)
);
CREATE INDEX customers_card_inx ON customers USING btree (card);
CREATE INDEX customers_name_inx ON customers USING btree (name);
CREATE UNIQUE INDEX customers_skey_inx ON customers USING btree (searchkey);
CREATE INDEX customers_taxid_inx ON customers USING btree (taxid);