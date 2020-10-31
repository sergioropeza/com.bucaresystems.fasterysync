CREATE OR REPLACE VIEW bsca_taxes_v
AS SELECT c_tax.c_tax_id AS id,
    c_tax.name,
    c_tax.c_taxcategory_id AS category,
    c_tax.rate / 100::numeric AS rate,
    c_tax.parent_tax_id AS parentid,
    c_tax.c_tax_id AS idempiere_ID
   FROM c_tax;