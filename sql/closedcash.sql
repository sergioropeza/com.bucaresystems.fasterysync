CREATE TABLE closedcash (
	"money" varchar(255) NOT NULL,
	host varchar(255) NOT NULL,
	hostsequence int4 NOT NULL,
	datestart timestamp NOT NULL,
	dateend timestamp NULL DEFAULT NULL::timestamp without time zone,
	nosales int4 NOT NULL DEFAULT 0,
	orgvalue varchar(255)  DEFAULT NULL,
	CONSTRAINT closedcash_inx_seq UNIQUE (host, hostsequence),
	CONSTRAINT closedcash_pkey PRIMARY KEY (money)
);
CREATE INDEX closedcash_inx_1 ON closedcash USING btree (datestart);