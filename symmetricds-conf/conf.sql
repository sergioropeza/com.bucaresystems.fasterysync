--nodes 
insert into sym_node_group (node_group_id)(select 'corp' where (select 1 from sym_node_group where node_group_id = 'corp') is null);
insert into sym_node_group (node_group_id)(select 'store' where (select 1 from sym_node_group where node_group_id = 'store') is null);
insert into sym_node_group (node_group_id)(select 'workstation' where (select 1 from sym_node_group where node_group_id = 'workstation') is null);

------------------------------------------------------------------------------
-- Node Group Links
------------------------------------------------------------------------------

insert into sym_node_group_link (source_node_group_id, target_node_group_id, data_event_action) values ('corp', 'store', 'W');
insert into sym_node_group_link (source_node_group_id, target_node_group_id, data_event_action) values ('store', 'corp', 'P');
insert into sym_node_group_link (source_node_group_id, target_node_group_id, data_event_action) values ('store', 'workstation', 'W');
insert into sym_node_group_link (source_node_group_id, target_node_group_id, data_event_action) values ('workstation', 'store', 'P');

------------------------------------------------------------------------------
-- Channels
------------------------------------------------------------------------------

insert into sym_channel (channel_id, processing_order, max_batch_size, enabled, description)
				  values('products', 1, 100000, 1, 'Product and pricing data');
insert into sym_channel (channel_id, processing_order, max_batch_size, enabled, description)
				  values('users', 50, 100000, 1, 'Users');
insert into sym_channel (channel_id, processing_order, max_batch_size, enabled, description)
				  values('customers', 50, 100000, 1, 'Product and pricing data');
insert into sym_channel (channel_id, processing_order, max_batch_size, enabled, description)
				  values('sales', 50, 100000, 1, 'Product and pricing data');
insert into sym_channel (channel_id, processing_order, max_batch_size, enabled, description)
				  values('currency', 50, 100000, 1, 'Currency and Multyply Rate');

 ------------------------------------------------------------------------------
-- Triggers
------------------------------------------------------------------------------

insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('people','pos','people','users',current_timestamp,current_timestamp,1);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('roles','pos','roles','users',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('categories','pos','categories','products',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('products','pos','products','products',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('products_value','pos','products_value','products',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('taxcategories','pos','taxcategories','products',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('taxes','pos','taxes','products',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('customers','pos','customers','customers',current_timestamp,current_timestamp,1);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('receipts','pos','receipts','sales',current_timestamp,current_timestamp,1);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('payments','pos','payments','sales',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('tickets','pos','tickets','sales',current_timestamp,current_timestamp,1);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('ticketlines','pos','ticketlines','sales',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('closedcash','pos','closedcash','sales',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('bsca_currency','pos','bsca_currency','currency',current_timestamp,current_timestamp,1);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time,sync_on_incoming_batch)
				  values('bsca_postendertype','pos','bsca_postendertype','sales',current_timestamp,current_timestamp,1);		

				  
------------------------------------------------------------------------------
-- Routers
------------------------------------------------------------------------------

-- Default router sends all data from corp to store 
insert into sym_router 
(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time)
values('corp_2_store', 'corp', 'store', 'default',current_timestamp, current_timestamp);

insert into sym_router 
(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time)
values('store_2_corp', 'store', 'corp', 'default',current_timestamp, current_timestamp);

insert into sym_router 
(router_id,source_node_group_id,target_node_group_id,router_type,router_expression,create_time,last_update_time)
values('corp_2_one_store', 'corp', 'store', 'column','node_id = :NODE_ID',current_timestamp, current_timestamp);

insert into sym_router 
(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time)
values('store_2_workstation', 'store', 'workstation', 'default',current_timestamp, current_timestamp);

insert into sym_router 
(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time)
values('workstation_2_store', 'workstation', 'store', 'default',current_timestamp, current_timestamp);

------------------------------------------------------------------------------
-- Trigger Routers
------------------------------------------------------------------------------

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('people','corp_2_one_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('people','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('roles','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('roles','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('categories','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('categories','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products','corp_2_one_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products_value','corp_2_one_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products_value','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxcategories','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxcategories','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxes','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxes','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('customers','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('customers','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('customers','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('customers','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('receipts','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('receipts','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('payments','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('payments','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('tickets','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('tickets','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('ticketlines','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('ticketlines','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('closedcash','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('closedcash','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_currency','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_currency','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_postendertype','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_postendertype','store_2_workstation', 100, current_timestamp, current_timestamp);


-- Triggers with capture disabled, so they are used for initial load only

insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('people_initload','pos','people','users',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('roles_initload','pos','roles','users',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('categories_initload','pos','categories','products',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('products_initload','pos','products','products',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('products_value_initload','pos','products_value','products',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('taxcategories_initload','pos','taxcategories','products',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('taxes_initload','pos','taxes','products',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('customers_initload','pos','customers','customers',current_timestamp,current_timestamp,0,0,0);		
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('bsca_currency_initload','pos','bsca_currency','currency',current_timestamp,current_timestamp,0,0,0);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time, sync_on_insert, sync_on_update, sync_on_delete)
				  values('bsca_postendertype_initload','pos','bsca_postendertype','sales',current_timestamp,current_timestamp,0,0,0);	
				 
				 				 
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('people_initload','corp_2_one_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('people_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('customers_initload','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('customers_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('roles_initload','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('roles_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('categories_initload','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('categories_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products_initload','corp_2_one_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products_value_initload','corp_2_one_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('products_value_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxcategories_initload','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxcategories_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxes_initload','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('taxes_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_currency_initload','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_currency_initload','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_postendertype_initload','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('bsca_postendertype_initload','store_2_workstation', 100, current_timestamp, current_timestamp);
				  
				 
				 

insert into sym_parameter(external_id, node_group_id, param_key, param_value, create_time, last_update_by, last_update_time)
(select 'ALL','ALL','auto.registration','true',null,'admin',null where (select distinct 1 from sym_parameter where  external_id = 'ALL' and node_group_id = 'ALL' and param_key = 'auto.registration') is null);


