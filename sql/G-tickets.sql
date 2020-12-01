CREATE TABLE pos.tickets (
	id varchar NOT NULL,
	tickettype int4 NOT NULL DEFAULT 0,
	ticketid int4 NOT NULL,
	person varchar NOT NULL,
	customer varchar NULL,
	status int4 NOT NULL DEFAULT 0,
	bsca_fiscaldocumentno varchar(255) NULL,
	bsca_machinefiscalnumber varchar(255) NULL,
	CONSTRAINT tickets_pkey PRIMARY KEY (id)
);
CREATE INDEX tickets_ticketid ON pos.tickets USING btree (tickettype, ticketid);