CREATE OR REPLACE VIEW pos.bsca_categories_v
AS SELECT m_product_category.m_product_category_id AS id,
    m_product_category.name,
    m_product_category.m_product_category_id AS idempiere_id
   FROM m_product_category
  WHERE m_product_category.isactive = 'Y'::bpchar;