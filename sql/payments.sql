CREATE TABLE payments (
	id varchar(255) NOT NULL,
	receipt varchar(255) NOT NULL,
	payment varchar(255) NOT NULL,
	total float8 NOT NULL DEFAULT '0'::double precision,
	tip float8 NULL DEFAULT '0'::double precision,
	transid varchar(255) NULL DEFAULT NULL::character varying,
	isprocessed bool NULL DEFAULT false,
	returnmsg bytea NULL,
	notes varchar(255) NULL DEFAULT NULL::character varying,
	tendered float8 NULL,
	cardname varchar(255) NULL DEFAULT NULL::character varying,
	voucher varchar(255) NULL DEFAULT NULL::character varying,
	orgvalue varchar(255)  DEFAULT NULL,
	multiplyrate varchar(255)  DEFAULT NULL,
	CONSTRAINT pk_tr_payments PRIMARY KEY (id),
	CONSTRAINT payments_fk_receipt FOREIGN KEY (receipt) REFERENCES receipts(id)
);
CREATE INDEX payments_payment_idx ON payments USING btree (payment);