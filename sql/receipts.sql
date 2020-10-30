CREATE table receipts (
	id varchar(255) NOT NULL,
	money varchar(255) NOT NULL,
	datenew timestamp NOT NULL,
	attributes bytea NULL,
	person varchar(255) NULL DEFAULT NULL::character varying,
	orgvalue varchar(255) NULL DEFAULT NULL::character varying,
	bsca_isimported bool NULL DEFAULT false,
	CONSTRAINT pk_tr_receipts PRIMARY KEY (id)
);
CREATE INDEX receipts_datenew_idx ON receipts USING btree (datenew);