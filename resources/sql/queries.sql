-- :name create-user! :? :1
-- :doc creates a new user record, returning the assigned id
WITH id_row AS (
  INSERT INTO users
    (username, first_name, last_name, email)
  VALUES (:username, :first_name, :last_name, :email) RETURNING id
)
SELECT id FROM id_row

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id
