CREATE OR REPLACE VIEW pos.bsca_currency_v
AS SELECT cc.c_currency_id AS id,
    cc.description AS name,
    cc.iso_code AS isocode,
    cc.cursymbol,
    0 AS multiplyrate,
        CASE
            WHEN cc.isactive = 'Y'::bpchar THEN true
            ELSE false
        END AS isactive,
    NULL::text AS node_id,
    cc.c_currency_id AS idempiere_id
   FROM c_currency cc
  WHERE cc.isactive = 'Y'::bpchar
  GROUP BY cc.iso_code, cc.c_currency_id, cc.description, cc.cursymbol, cc.isactive;