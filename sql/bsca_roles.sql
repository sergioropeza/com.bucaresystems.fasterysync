CREATE OR REPLACE VIEW bsca_roles_v
AS SELECT rp.bsca_rolespos_id AS id,
    rp.name,
    org.value AS node_id,
    NULL::bytea AS permissions,
    rp.bsca_rolespos_id AS idempiere_id,
    rp.help AS bsca_permissions
   FROM bsca_rolespos rp
     JOIN ad_org org ON org.ad_org_id = rp.ad_org_id;