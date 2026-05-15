-- Clears all clients and resets the clients id sequence
DELETE FROM clients;

-- Reset the identity/sequence for the `clients.id` column (H2 syntax)
ALTER TABLE clients ALTER COLUMN id RESTART WITH 1;

-- Optional: verify
SELECT COUNT(*) FROM clients;
