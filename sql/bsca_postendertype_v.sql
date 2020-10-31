CREATE OR REPLACE VIEW bsca_postendertype_v
AS SELECT p.c_postendertype_id AS id,
    p.name,
    p.c_currency_id AS bsca_currency_id,
        CASE
            WHEN p.isallowchange = 'Y'::bpchar THEN true
            ELSE false
        END AS isallowchange,
        CASE
            WHEN p.issetdifference = 'Y'::bpchar THEN true
            ELSE false
        END AS issetdifference,
        CASE
            WHEN p.isactive = 'Y'::bpchar THEN true
            ELSE false
        END AS isactive,
    p.classname,
    NULL::text AS node_id,
    p.c_postendertype_id AS idempiere_id
   FROM c_postendertype p;