
CREATE TABLE pos.roles ( id varchar NOT NULL, "name" varchar NOT NULL, permissions bytea NULL, node_id varchar(255) NULL, idempiere_id numeric NULL, bsca_permissions text NULL, CONSTRAINT role_uk UNIQUE (name, node_id), CONSTRAINT roles_pk PRIMARY KEY (id) );
CREATE TABLE pos.people ( id varchar NOT NULL, "name" varchar NOT NULL, apppassword varchar NULL, card varchar NULL, "role" varchar NOT NULL, visible bool NOT NULL, image bytea NULL, node_id varchar(25) NULL, ad_user_id numeric NULL, idempiere_ID numeric NULL, CONSTRAINT people_name_inx UNIQUE (name), CONSTRAINT pk PRIMARY KEY (id), CONSTRAINT people_fk_1 FOREIGN KEY (role) REFERENCES pos.roles(id) );
CREATE TABLE pos.customers ( id varchar NOT NULL, searchkey varchar NOT NULL, taxid varchar NULL, "name" varchar NOT NULL, taxcategory varchar NULL, card varchar NULL, maxdebt float8 NOT NULL DEFAULT 0, address varchar NULL, address2 varchar NULL, postal varchar NULL, city varchar NULL, region varchar NULL, country varchar NULL, firstname varchar NULL, lastname varchar NULL, email varchar NULL, phone varchar NULL, phone2 varchar NULL, fax varchar NULL, notes varchar NULL, visible bool NOT NULL DEFAULT true, curdate timestamp NULL, curdebt float8 NULL DEFAULT 0, image bytea NULL, isvip bool NOT NULL DEFAULT true, discount float8 NULL DEFAULT 0, memodate timestamp NULL DEFAULT now(), gender varchar(255) NULL, CONSTRAINT customers_skey_inx UNIQUE (searchkey), CONSTRAINT pk_customers PRIMARY KEY (id) ); CREATE INDEX customers_card_inx ON pos.customers USING btree (card); CREATE INDEX customers_name_inx ON pos.customers USING btree (name); CREATE INDEX customers_taxid_inx ON pos.customers USING btree (taxid);
CREATE TABLE pos.taxcategories ( id varchar NOT NULL, "name" varchar NOT NULL, idempiere_id numeric NULL, CONSTRAINT taxcategories_pkey PRIMARY KEY (id) ); CREATE UNIQUE INDEX taxcat_name_inx ON pos.taxcategories USING btree (name);
CREATE TABLE pos.categories ( id varchar NOT NULL, "name" varchar NOT NULL, parentid varchar NULL, image bytea NULL, texttip varchar NULL, catshowname bool NOT NULL DEFAULT true, catorder varchar NULL, idempiere_ID numeric NULL, CONSTRAINT categories_pkey PRIMARY KEY (id), CONSTRAINT categories_fk_1 FOREIGN KEY (parentid) REFERENCES pos.categories(id) ); CREATE UNIQUE INDEX categories_fk_1 ON pos.categories USING btree (parentid); CREATE UNIQUE INDEX categories_name_inx ON pos.categories USING btree (name);
CREATE TABLE pos.bsca_currency ( id varchar(255) NOT NULL, "name" varchar(255) NOT NULL, isocode varchar(5) NOT NULL, cursymbol varchar(10) NOT NULL, multiplyrate float8 NOT NULL DEFAULT '1'::double precision, isconversioncurrency bool NOT NULL DEFAULT false, isactive bool NOT NULL DEFAULT true, node_id varchar(25) NULL, idempiere_id numeric NULL, CONSTRAINT bsca_currency_pkey PRIMARY KEY (id), CONSTRAINT currency_inx_0 UNIQUE (isocode), CONSTRAINT currency_inx_1 UNIQUE (cursymbol) );
CREATE TABLE pos.bsca_postendertype ( id varchar(255) NOT NULL, "name" varchar(255) NOT NULL, bsca_currency_id varchar(255) NOT NULL, isallowchange bool NOT NULL DEFAULT false, issetdifference bool NOT NULL DEFAULT false, classname text NULL, isactive bool NOT NULL DEFAULT true, node_id varchar(25) NULL, idempiere_id numeric NULL, CONSTRAINT bsca_postendertype_pkey PRIMARY KEY (id), CONSTRAINT bsca_postendertype_fk FOREIGN KEY (bsca_currency_id) REFERENCES pos.bsca_currency(id) ); CREATE INDEX bsca_postendertype_fk ON pos.bsca_postendertype USING btree (bsca_currency_id);
CREATE table pos.receipts ( id varchar NOT NULL, "money" varchar NOT NULL, datenew timestamp NOT NULL, "attributes" bytea NULL, person varchar NULL, orgvalue varchar(255) NULL DEFAULT NULL::character varying, bsca_isimported bool NULL DEFAULT false, CONSTRAINT pk_tr_receipts PRIMARY KEY (id) ); CREATE INDEX receipts_datenew_idx ON pos.receipts USING btree (datenew);
CREATE TABLE pos.tickets ( id varchar NOT NULL, tickettype int4 NOT NULL DEFAULT 0, ticketid int4 NOT NULL, person varchar NOT NULL, customer varchar NULL, status int4 NOT NULL DEFAULT 0, bsca_fiscaldocumentno varchar(255) NULL, bsca_machinefiscalnumber varchar(255) NULL, CONSTRAINT tickets_pkey PRIMARY KEY (id), CONSTRAINT tickets_customers_fk FOREIGN KEY (customer) REFERENCES customers(id), CONSTRAINT tickets_fk_2 FOREIGN KEY (person) REFERENCES people(id), CONSTRAINT tickets_fk_id FOREIGN KEY (id) REFERENCES receipts(id) ); CREATE INDEX tickets_ticketid ON pos.tickets USING btree (tickettype, ticketid);
CREATE TABLE pos.closedcash ( "money" varchar(255) NOT NULL, host varchar(255) NOT NULL, hostsequence int4 NOT NULL, datestart timestamp NOT NULL, dateend timestamp NULL DEFAULT NULL::timestamp without time zone, nosales int4 NOT NULL DEFAULT 0, orgvalue varchar(255)  DEFAULT NULL, CONSTRAINT closedcash_inx_seq UNIQUE (host, hostsequence), CONSTRAINT closedcash_pkey PRIMARY KEY (money) ); CREATE INDEX closedcash_inx_1 ON pos.closedcash USING btree (datestart);
CREATE TABLE pos.payments ( id varchar NOT NULL, receipt varchar NOT NULL, payment varchar NOT NULL, total float8 NOT NULL DEFAULT 0, tip float8 NOT NULL DEFAULT 0, transid varchar NULL, isprocessed bool NOT NULL DEFAULT false, returnmsg bytea NULL, notes varchar NULL, tendered float8 NOT NULL DEFAULT 0, cardname varchar NULL, voucher varchar NULL, numseq_vpos text NOT NULL, bsca_postendertype_id varchar(255) NULL, orgvalue varchar(255) NULL DEFAULT NULL::character varying, multiplyrate float8 NOT NULL DEFAULT 1, CONSTRAINT payments_pkey PRIMARY KEY (id), CONSTRAINT payments_fk_receipt FOREIGN KEY (receipt) REFERENCES receipts(id), CONSTRAINT paymentstendertype_fx FOREIGN KEY (bsca_postendertype_id) REFERENCES bsca_postendertype(id) ); CREATE INDEX payments_payment_idx ON pos.payments USING btree (payment);
CREATE TABLE pos.products ( id varchar NOT NULL, reference varchar NOT NULL, code varchar NOT NULL, codetype varchar NULL, "name" varchar NOT NULL, pricebuy float8 NOT NULL DEFAULT 0, pricesell float8 NOT NULL DEFAULT 0, category varchar NOT NULL, taxcat varchar NOT NULL, attributeset_id varchar NULL, stockcost float8 NOT NULL DEFAULT 0, stockvolume float8 NOT NULL DEFAULT 0, image bytea NULL, iscom bool NOT NULL DEFAULT false, isscale bool NOT NULL DEFAULT false, isconstant bool NOT NULL DEFAULT false, printkb bool NOT NULL DEFAULT false, sendstatus bool NOT NULL DEFAULT false, isservice bool NOT NULL DEFAULT false, "attributes" bytea NULL, display varchar NULL, isvprice bool NOT NULL DEFAULT false, isverpatrib bool NOT NULL DEFAULT false, texttip varchar NULL, warranty bool NOT NULL DEFAULT false, stockunits float8 NOT NULL DEFAULT 0, printto varchar NULL DEFAULT '1'::character varying, supplier varchar NULL, uom varchar NULL DEFAULT '0'::character varying, memodate timestamp NULL DEFAULT now(), node_id varchar(255) NULL, m_pricelist_version_id numeric(10) NULL, idempiere_id numeric NULL, ad_org_id numeric NULL, CONSTRAINT products_inx_0 UNIQUE (reference, node_id), CONSTRAINT products_inx_1 UNIQUE (code, node_id), CONSTRAINT products_pkey PRIMARY KEY (id), CONSTRAINT products_fk_1 FOREIGN KEY (category) REFERENCES pos.categories(id), CONSTRAINT products_taxcat_fk FOREIGN KEY (taxcat) REFERENCES pos.taxcategories(id) ); CREATE UNIQUE INDEX products_attrset_fx ON pos.products USING btree (attributeset_id);
CREATE TABLE pos.products_value ( id varchar(255) NOT NULL, product varchar(255) NOT NULL, value varchar(255) NOT NULL, isactive bool NOT NULL DEFAULT true, ismastervalue bool NOT NULL DEFAULT false, isplucode bool NOT NULL DEFAULT false, isdefault bool NOT NULL DEFAULT false, isdefaultvalue bool NOT NULL DEFAULT false, node_id varchar(255) NULL, m_product_id numeric NULL, ad_org_id numeric NULL, idempiere_id numeric NULL, qty numeric NOT NULL DEFAULT 1, CONSTRAINT products_value_pkey PRIMARY KEY (id), CONSTRAINT products_value_fk_1 FOREIGN KEY (product) REFERENCES pos.products(id) );
CREATE TABLE pos.taxes ( id varchar NOT NULL, "name" varchar NOT NULL, category varchar NOT NULL, custcategory varchar NULL, parentid varchar NULL, rate float8 NOT NULL DEFAULT 0, ratecascade bool NOT NULL DEFAULT false, rateorder int4 NULL, idempiere_id numeric NULL, CONSTRAINT pk_taxes PRIMARY KEY (id), CONSTRAINT taxes_name_inx UNIQUE (name), CONSTRAINT taxes_cat_fk FOREIGN KEY (category) REFERENCES pos.taxcategories(id) );
CREATE TABLE pos.ticketlines ( ticket varchar NOT NULL, line int4 NOT NULL, product varchar NULL, attributesetinstance_id varchar NULL, units float8 NOT NULL DEFAULT 0, price float8 NOT NULL DEFAULT 0, taxid varchar NOT NULL, "attributes" bytea NULL, productcode varchar(255) NULL, orgvalue varchar(255) NULL DEFAULT NULL::character varying, CONSTRAINT pk_ticketlines PRIMARY KEY (ticket, line), CONSTRAINT ticketlines_fk_2 FOREIGN KEY (ticket) REFERENCES pos.tickets(id), CONSTRAINT ticketlines_fk_3 FOREIGN KEY (product) REFERENCES pos.products(id) );
CREATE OR REPLACE VIEW pos.bsca_categories_v AS SELECT m_product_category.m_product_category_id AS id, m_product_category.name, m_product_category.m_product_category_id AS idempiere_id FROM m_product_category WHERE m_product_category.isactive = 'Y'::bpchar;
CREATE OR REPLACE VIEW pos.bsca_currency_v AS SELECT cc.c_currency_id AS id, cc.description AS name, cc.iso_code AS isocode, cc.cursymbol, 1 AS multiplyrate, CASE WHEN cc.isactive = 'Y'::bpchar THEN true ELSE false END AS isactive, NULL::text AS node_id, cc.c_currency_id AS idempiere_id FROM c_currency cc WHERE cc.isactive = 'Y'::bpchar GROUP BY cc.iso_code, cc.c_currency_id, cc.description, cc.cursymbol, cc.isactive;
CREATE OR REPLACE VIEW pos.bsca_people_v AS SELECT (u.ad_user_id || ''::text) || u.ad_org_id AS id, u.name, u.password AS apppassword, rp.bsca_rolespos_id AS role, u.ad_user_id, u.ad_user_id AS idempiere_id, o.value AS node_id FROM ad_user u JOIN ad_org o ON o.ad_org_id = u.ad_org_id JOIN bsca_rolespos rp ON u.bsca_rolespos_id = rp.bsca_rolespos_id WHERE u.bsca_isposuser = 'Y'::bpchar;
CREATE OR REPLACE VIEW pos.bsca_postendertype_v AS SELECT p.c_postendertype_id AS id, p.name, p.c_currency_id AS bsca_currency_id, CASE WHEN p.isallowchange = 'Y'::bpchar THEN true ELSE false END AS isallowchange, CASE WHEN p.issetdifference = 'Y'::bpchar THEN true ELSE false END AS issetdifference, CASE WHEN p.isactive = 'Y'::bpchar THEN true ELSE false END AS isactive, p.classname, NULL::text AS node_id, p.c_postendertype_id AS idempiere_id FROM c_postendertype p;
CREATE OR REPLACE VIEW pos.bsca_products_value_v AS SELECT (pv.bsca_productvalue_id::text || pv.m_product_id) || org.ad_org_id AS id, pv.m_product_id::text || org.ad_org_id AS product, pv.value, CASE WHEN pv.isactive = 'Y'::bpchar THEN true ELSE false END AS isactive, CASE WHEN pv.ismastervalue = 'Y'::bpchar THEN true ELSE false END AS ismastervalue, CASE WHEN pv.isplucode = 'Y'::bpchar THEN true ELSE false END AS isplucode, CASE WHEN pv.isdefault = 'Y'::bpchar THEN true ELSE false END AS isdefault, CASE WHEN pv.isdefaultvalue = 'Y'::bpchar THEN true ELSE false END AS isdefaultvalue, org.value AS node_id, pv.bsca_productvalue_id AS idempiere_id, org.ad_org_id, pv.m_product_id, pv.qty FROM bsca_productvalue pv JOIN bsca_productorg po ON po.m_product_id = pv.m_product_id JOIN ad_org org ON org.ad_org_id = po.ad_org_id;
CREATE OR REPLACE VIEW pos.bsca_products_v AS SELECT p.m_product_id::text || po.ad_org_id AS id, p.sku AS reference, p.sku AS code, 'CODE128'::text AS codetype, p.name, 0 AS pricebuy, COALESCE(pp.pricelist, 0::numeric) AS pricesell, pc.m_product_category_id AS category, p.c_taxcategory_id AS taxcat, oi.value AS node_id, p.m_product_id as idempiere_id, pp.m_pricelist_version_id, oi.ad_org_id FROM m_product p JOIN c_taxcategory tc ON tc.c_taxcategory_id = p.c_taxcategory_id JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id JOIN bsca_productorg po ON po.m_product_id = p.m_product_id JOIN m_productprice pp ON pp.m_product_id = p.m_product_id AND po.ad_org_id = pp.ad_org_id JOIN m_pricelist_version plv ON plv.m_pricelist_version_id = pp.m_pricelist_version_id JOIN m_pricelist pl ON pl.m_pricelist_id = plv.m_pricelist_id JOIN ad_org oi ON oi.ad_org_id = po.ad_org_id AND pl.isdefault = 'Y'::bpchar AND pl.issopricelist = 'Y'::bpchar WHERE p.isactive = 'Y'::bpchar;
CREATE OR REPLACE VIEW pos.bsca_roles_v AS SELECT rp.bsca_rolespos_id AS id, rp.name, org.value AS node_id, NULL::bytea AS permissions, rp.bsca_rolespos_id AS idempiere_id, rp.help AS bsca_permissions FROM bsca_rolespos rp JOIN ad_org org ON org.ad_org_id = rp.ad_org_id;
CREATE OR REPLACE VIEW pos.bsca_taxcategories_v AS SELECT c_taxcategory.c_taxcategory_id AS id, c_taxcategory.name, c_taxcategory.c_taxcategory_id AS idempiere_id FROM c_taxcategory;
CREATE OR REPLACE VIEW pos.bsca_taxes_v AS SELECT c_tax.c_tax_id AS id, c_tax.name, c_tax.c_taxcategory_id AS category, c_tax.rate / 100::numeric AS rate, c_tax.parent_tax_id AS parentid, c_tax.c_tax_id AS idempiere_ID FROM c_tax;
