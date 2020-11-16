CREATE ROLE pos SUPERUSER LOGIN PASSWORD 'pos';
CREATE DATABASE unicentaopos WITH owner pos;
CREATE SCHEMA pos AUTHORIZATION pos;
ALTER ROLE pos SET search_path TO pos, pg_catalog;
CREATE ROLE symmetricds SUPERUSER LOGIN PASSWORD 'symmetricds';
CREATE SCHEMA symmetricds AUTHORIZATION symmetricds;
ALTER ROLE symmetricds SET search_path TO symmetricds, pg_catalog;

