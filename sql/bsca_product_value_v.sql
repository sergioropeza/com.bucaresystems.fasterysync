CREATE OR REPLACE VIEW pos.bsca_products_value_v
AS SELECT (pv.bsca_productvalue_id::text || pv.m_product_id) || org.ad_org_id AS id,
    pv.m_product_id::text || org.ad_org_id AS product,
    pv.value,
        CASE
            WHEN pv.isactive = 'Y'::bpchar THEN true
            ELSE false
        END AS isactive,
        CASE
            WHEN pv.ismastervalue = 'Y'::bpchar THEN true
            ELSE false
        END AS ismastervalue,
        CASE
            WHEN pv.isplucode = 'Y'::bpchar THEN true
            ELSE false
        END AS isplucode,
        CASE
            WHEN pv.isdefault = 'Y'::bpchar THEN true
            ELSE false
        END AS isdefault,
        CASE
            WHEN pv.isdefaultvalue = 'Y'::bpchar THEN true
            ELSE false
        END AS isdefaultvalue,
    org.value AS node_id,
    pv.bsca_productvalue_id AS idempiere_id,
    org.ad_org_id,
    pv.m_product_id,
    pv.qty
   FROM bsca_productvalue pv
     JOIN bsca_productorg po ON po.m_product_id = pv.m_product_id
     JOIN ad_org org ON org.ad_org_id = po.ad_org_id;