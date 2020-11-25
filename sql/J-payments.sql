CREATE TABLE pos.payments (
		id varchar NOT NULL,
	receipt varchar NOT NULL,
	payment varchar NOT NULL,
	total float8 NOT NULL DEFAULT 0,
	tip float8 NOT NULL DEFAULT 0,
	transid varchar NULL,
	isprocessed bool NOT NULL DEFAULT false,
	returnmsg bytea NULL,
	notes varchar NULL,
	tendered float8 NOT NULL DEFAULT 0,
	cardname varchar NULL,
	voucher varchar NULL,
	numseq_vpos text NOT NULL,
	bsca_postendertype_id varchar(255) NULL,
	orgvalue varchar(255) NULL DEFAULT NULL::character varying,
	multiplyrate float8 NOT NULL DEFAULT 1,
	CONSTRAINT payments_pkey PRIMARY KEY (id));
CREATE INDEX payments_payment_idx ON pos.payments USING btree (payment);
