CREATE ROLE pos SUPERUSER LOGIN PASSWORD 'f4st3ry2020';
CREATE ROLE symmetricds SUPERUSER LOGIN PASSWORD 'f4st3ry2020';
CREATE DATABASE unicentaopos WITH owner pos;
\connect unicentaopos;

CREATE SCHEMA pos AUTHORIZATION pos;
ALTER ROLE pos SET search_path TO pos, pg_catalog;
CREATE SCHEMA symmetricds AUTHORIZATION symmetricds;
ALTER ROLE symmetricds SET search_path TO symmetricds, pg_catalog;

--Create on Store 

ALTER TABLE pos.payments DROP CONSTRAINT payments_fk_receipt;
ALTER TABLE pos.ticketlines DROP CONSTRAINT ticketlines_fk_ticket;
ALTER TABLE pos.tickets DROP CONSTRAINT tickets_customers_fk;
ALTER TABLE pos.tickets DROP CONSTRAINT tickets_fk_2;
ALTER TABLE pos.tickets DROP CONSTRAINT tickets_fk_id;
ALTER TABLE pos.taxes DROP CONSTRAINT taxes_cat_fk;
ALTER TABLE pos.products DROP CONSTRAINT products_taxcat_fk;

