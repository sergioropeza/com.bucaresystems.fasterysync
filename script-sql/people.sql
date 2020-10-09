CREATE TABLE pos.people (
	id varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	apppassword varchar(255) NULL DEFAULT NULL::character varying,
	card varchar(255) NULL DEFAULT NULL::character varying,
	"role" varchar(255) NOT NULL,
	visible bool NOT NULL DEFAULT true, 
	image bytea NULL,
	node_id varchar(25) NULL,
	ad_user_id numeric NULL,
	idempiere_ID numeric NULL,
	CONSTRAINT people_name_inx UNIQUE (name),
	CONSTRAINT pk PRIMARY KEY (id),
	CONSTRAINT people_fk_1 FOREIGN KEY (role) REFERENCES roles(id)
);
