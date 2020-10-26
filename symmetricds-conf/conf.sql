--nodes 
insert into sym_node_group (node_group_id) values ('corp');
insert into sym_node_group (node_group_id) values ('store');
insert into sym_node_group (node_group_id) values ('workstation');

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

 ------------------------------------------------------------------------------
-- Triggers
------------------------------------------------------------------------------

insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.people','pos','people','users',current_timestamp,current_timestamp);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.roles','pos','roles','users',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.categories','pos','products','products',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.products','pos','products','products',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.products_value','pos','products_value','products',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.taxcategories','pos','taxcategories','products',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.taxes','pos','taxes','products',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.customers','pos','customers','customers',current_timestamp,current_timestamp);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.receipts','pos','receipts','sales',current_timestamp,current_timestamp);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.payments','pos','payments','sales',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.tickets','pos','tickets','sales',current_timestamp,current_timestamp);	
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.ticketlines','pos','ticketlines','sales',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.closedcash','pos','closedcash','sales',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.bsca_currency','pos','bsca_currency','sales',current_timestamp,current_timestamp);
insert into sym_trigger (trigger_id,source_schema_name,source_table_name,channel_id,last_update_time,create_time)
				  values('pos.bsca_postendertype','pos','bsca_postendertype','sales',current_timestamp,current_timestamp);		

				  
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
values('pos.people','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.people','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.roles','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.roles','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.categories','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.categories','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.products','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.products','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.products_value','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.products_value','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.taxcategories','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.taxcategories','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.taxes','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.taxes','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.customers','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.customers','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.receipts','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.receipts','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.payments','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.payments','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.tickets','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.tickets','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.ticketlines','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.ticketlines','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.closedcash','workstation_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.closedcash','store_2_corp', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.bsca_currency','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.bsca_currency','store_2_workstation', 100, current_timestamp, current_timestamp);

insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.bsca_postendertype','corp_2_store', 100, current_timestamp, current_timestamp);
insert into sym_trigger_router 
(trigger_id,router_id,initial_load_order,last_update_time,create_time)
values('pos.bsca_postendertype','store_2_workstation', 100, current_timestamp, current_timestamp);


