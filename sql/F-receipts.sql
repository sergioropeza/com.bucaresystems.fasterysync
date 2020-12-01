CREATE table pos.receipts (
	id varchar NOT NULL,
	"money" varchar NOT NULL,
	datenew timestamp NOT NULL,
	"attributes" bytea NULL,
	person varchar NULL,
	orgvalue varchar(255) NOT NULL ,
	subtotal double precision NOT NULL,
	taxamt double precision NOT NULL,
	GrandTotal double precision NOT NULL,
	bsca_isimported bool NULL DEFAULT false,
	CONSTRAINT pk_tr_receipts PRIMARY KEY (id)
);
CREATE INDEX receipts_datenew_idx ON pos.receipts USING btree (datenew);