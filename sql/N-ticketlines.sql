CREATE TABLE pos.ticketlines (
	ticket varchar(255) NOT NULL,
	line int4 NOT NULL,
	product varchar(255) NULL DEFAULT NULL::character varying,
	attributesetinstance_id varchar(255) NULL DEFAULT NULL::character varying,
	units float8 NOT NULL,
	price float8 NOT NULL,
	taxid varchar(255) NOT NULL,
	attributes bytea NULL,
	productcode varchar(255) NULL,
	orgvalue varchar(255)  DEFAULT NULL,
	CONSTRAINT pk_ticketlines PRIMARY KEY (ticket, line),
	CONSTRAINT ticketlines_fk_2 FOREIGN KEY (ticket) REFERENCES pos.tickets(id),
	CONSTRAINT ticketlines_fk_3 FOREIGN KEY (product) REFERENCES pos.products(id)
);
