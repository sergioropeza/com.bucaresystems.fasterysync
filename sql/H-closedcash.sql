CREATE TABLE pos.closedcash (
	"money" varchar NOT NULL,
	host varchar NOT NULL,
	hostsequence int4 NOT NULL,
	datestart timestamp NOT NULL,
	dateend timestamp NULL,
	nosales int4 NOT NULL DEFAULT 0,
	orgvalue varchar(255) NULL DEFAULT NULL::character varying,
	people varchar(255) NULL DEFAULT NULL::character varying,
	CONSTRAINT closedcash_pkey PRIMARY KEY (money)
);
CREATE INDEX closedcash_inx_1 ON pos.closedcash USING btree (datestart);