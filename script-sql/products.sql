CREATE TABLE pos.products (
	id varchar(255) NOT NULL,
	reference varchar(255) NOT NULL,
	code varchar(255) NOT NULL,
	codetype varchar(255) NULL DEFAULT NULL::character varying,
	"name" varchar(255) NOT NULL,
	pricebuy float8 NOT NULL DEFAULT '0'::double precision,
	pricesell float8 NOT NULL DEFAULT '0'::double precision,
	category varchar(255) NOT NULL,
	taxcat varchar(255) NOT NULL,
	attributeset_id varchar(255) NULL DEFAULT NULL::character varying,
	stockcost float8 NOT NULL DEFAULT '0'::double precision,
	stockvolume float8 NOT NULL DEFAULT '0'::double precision,
	image bytea NULL,
	iscom bool NOT NULL DEFAULT false,
	isscale bool NOT NULL DEFAULT false,
	isconstant bool NOT NULL DEFAULT false,
	printkb bool NOT NULL DEFAULT false,
	sendstatus bool NOT NULL DEFAULT false,
	isservice bool NOT NULL DEFAULT false,
	"attributes" bytea NULL,
	display varchar(255) NULL DEFAULT NULL::character varying,
	isvprice int2 NOT NULL DEFAULT '0'::smallint,
	isverpatrib int2 NOT NULL DEFAULT '0'::smallint,
	texttip varchar(255) NULL DEFAULT NULL::character varying,
	warranty int2 NOT NULL DEFAULT '0'::smallint,
	stockunits float8 NOT NULL DEFAULT '0'::double precision,
	printto varchar(255) NULL DEFAULT '1'::character varying,
	supplier varchar(255) NULL DEFAULT NULL::character varying,
	uom varchar(255) NULL DEFAULT '0'::character varying,
	memodate timestamp NULL DEFAULT '2018-01-01 00:00:01'::timestamp without time zone,
	node_id varchar(255) NULL,
	m_pricelist_version_id numeric(10) NULL,
	idempiere_id numeric NULL,
	ad_org_id numeric NULL,
	CONSTRAINT products_inx_0 UNIQUE (reference, node_id),
	CONSTRAINT products_inx_1 UNIQUE (code, node_id),
	CONSTRAINT products_pkey PRIMARY KEY (id),
	CONSTRAINT products_fk_1 FOREIGN KEY (category) REFERENCES categories(id),
	CONSTRAINT products_taxcat_fk FOREIGN KEY (taxcat) REFERENCES taxcategories(id)
);
CREATE UNIQUE INDEX products_attrset_fx ON pos.products USING btree (attributeset_id);