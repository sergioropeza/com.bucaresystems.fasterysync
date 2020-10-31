CREATE OR REPLACE VIEW bsca_people_v
AS SELECT (u.ad_user_id || ''::text) || u.ad_org_id AS id,
    u.name,
    u.password AS apppassword,
    rp.bsca_rolespos_id AS role,
    u.ad_user_id,
    u.ad_user_id AS idempiere_id,
    o.value AS node_id
   FROM ad_user u
     JOIN ad_org o ON o.ad_org_id = u.ad_org_id
     JOIN bsca_rolespos rp ON u.bsca_rolespos_id = rp.bsca_rolespos_id
  WHERE u.bsca_isposuser = 'Y'::bpchar;