CREATE TABLE pos.ticketlines (
	ticket varchar NOT NULL,
	line int4 NOT NULL,
	product varchar NULL,
	attributesetinstance_id varchar NULL,
	units float8 NOT NULL DEFAULT 0,
	price float8 NOT NULL DEFAULT 0,
	taxid varchar NOT NULL,
	"attributes" bytea NULL,
	productcode varchar(255) NULL,
	orgvalue varchar(255) NULL DEFAULT NULL::character varying,
	CONSTRAINT pk_ticketlines PRIMARY KEY (ticket, line),
	CONSTRAINT ticketlines_fk_2 FOREIGN KEY (ticket) REFERENCES pos.tickets(id),
	CONSTRAINT ticketlines_fk_3 FOREIGN KEY (product) REFERENCES pos.products(id)
);
