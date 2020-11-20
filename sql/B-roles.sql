CREATE TABLE pos.roles (
	id varchar NOT NULL,
	"name" varchar NOT NULL,
	permissions bytea NULL,
	node_id varchar(255) NULL,
	idempiere_id numeric NULL,
	bsca_permissions text NULL,
	CONSTRAINT role_uk UNIQUE (name, node_id),
	CONSTRAINT roles_pk PRIMARY KEY (id)
);