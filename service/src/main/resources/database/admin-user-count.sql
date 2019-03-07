SELECT count(1) AS amount
FROM pg_catalog.pg_user u
WHERE u.usesuper = ?;