SELECT t.table_schema AS table_schema, count(1) AS table_amount
FROM information_schema.tables t
WHERE t.table_schema = ?
GROUP BY t.table_schema;