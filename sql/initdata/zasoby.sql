INSERT INTO warehouses (warehouse_name) VALUES
('Magazyn A', 'Lodz'),
('Magazyn B', 'Lodz');

INSERT INTO resources (resource_name, resource_type, resource_quantity, resource_status, warehouse_id, volunteer_name, assigned_task) VALUES
('Resource 1', 'Type A', 100, 'NIEPRZYDZIELONY', 1, NULL, NULL),
('Resource 2', 'Type B', 200, 'PRZYDZIELONY', 1, 'John Doe', 'Deliver to location X'),
('Resource 3', 'Type C', 50, 'NIEPRZYDZIELONY', 2, NULL, NULL);