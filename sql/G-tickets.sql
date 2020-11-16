CREATE TABLE pos.tickets (
	id varchar(255) NOT NULL,
	tickettype int4 NOT NULL DEFAULT 0,
	ticketid int4 NOT NULL,
	person varchar(255) NOT NULL,
	customer varchar(255) NULL DEFAULT NULL::character varying,
	status int4 NOT NULL DEFAULT 0,
	bsca_fiscaldocumentno varchar(255) DEFAULT NULL,
    bsca_machinefiscalnumber varchar(255) DEFAULT NULL,
	CONSTRAINT pk_tr_tickets PRIMARY KEY (id)
);
CREATE INDEX tickets_ticketid ON pos.tickets USING btree (tickettype, ticketid);