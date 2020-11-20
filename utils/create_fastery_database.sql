CREATE ROLE pos SUPERUSER LOGIN PASSWORD 'f4st3ry2020';
CREATE ROLE symmetricds SUPERUSER LOGIN PASSWORD 'f4st3ry2020';
CREATE DATABASE unicentaopos WITH owner pos;
\connect unicentaopos;

CREATE SCHEMA pos AUTHORIZATION pos;
ALTER ROLE pos SET search_path TO pos, pg_catalog;
CREATE SCHEMA symmetricds AUTHORIZATION symmetricds;
ALTER ROLE symmetricds SET search_path TO symmetricds, pg_catalog;

# PORT POSTGRESSQL 5482