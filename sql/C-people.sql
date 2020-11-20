CREATE TABLE pos.people (
	id varchar NOT NULL,
	"name" varchar NOT NULL,
	apppassword varchar NULL,
	card varchar NULL,
	"role" varchar NOT NULL,
	visible bool NOT NULL,
	image bytea NULL,
	node_id varchar(25) NULL,
	ad_user_id numeric NULL,
	idempiere_ID numeric NULL,
	CONSTRAINT people_name_inx UNIQUE (name),
	CONSTRAINT pk PRIMARY KEY (id),
	CONSTRAINT people_fk_1 FOREIGN KEY (role) REFERENCES pos.roles(id)
);