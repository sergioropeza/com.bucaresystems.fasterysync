CREATE OR REPLACE VIEW pos.bsca_taxcategories_v
AS SELECT c_taxcategory.c_taxcategory_id AS id,
    c_taxcategory.name,
    c_taxcategory.c_taxcategory_id AS idempiere_id
   FROM c_taxcategory;
   