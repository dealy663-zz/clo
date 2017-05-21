-- :name create-user! :? :1
-- :doc creates a new user record, returning the assigned id
--
-- params  password     The user's password, hopefully encrypted client side
--         user-name    The ID that the user will login with
--         first-name   The user's first name
--         last-name    The user's last name
--         email        The user's email address
SELECT create_user AS id FROM public.create_user(:password, :user-name, :first-name, :last-name, :email)
--;;

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id
--;;

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id
--;;

-- :name delete-user! :! :n
-- :doc delete a user given the user-name
DELETE FROM users
WHERE id = :id
