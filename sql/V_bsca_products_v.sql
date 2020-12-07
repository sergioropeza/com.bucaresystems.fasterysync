CREATE OR REPLACE VIEW pos.bsca_products_v
AS SELECT p.m_product_id::text || po.ad_org_id AS id,
    p.sku AS reference,
    p.sku AS code,
    'EAN-13'::text AS codetype,
    p.name,
    0 AS pricebuy,
    COALESCE(pp.pricelist, 0::numeric) AS pricesell,
    pc.m_product_category_id AS category,
    p.c_taxcategory_id AS taxcat,
    oi.value AS node_id,
    p.m_product_id AS idempiere_id,
    pp.m_pricelist_version_id,
    oi.ad_org_id, 
    p.bscatypestellar ='2'::text as isscale
   FROM m_product p
     JOIN c_taxcategory tc ON tc.c_taxcategory_id = p.c_taxcategory_id
     JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
     JOIN bsca_productorg po ON po.m_product_id = p.m_product_id
     JOIN m_productprice pp ON pp.m_product_id = p.m_product_id AND po.ad_org_id = pp.ad_org_id
     JOIN m_pricelist_version plv ON plv.m_pricelist_version_id = pp.m_pricelist_version_id
     JOIN m_pricelist pl ON pl.m_pricelist_id = plv.m_pricelist_id
     JOIN ad_org oi ON oi.ad_org_id = po.ad_org_id AND pl.isdefault = 'Y'::bpchar AND pl.issopricelist = 'Y'::bpchar
  WHERE p.isactive = 'Y'::bpchar;