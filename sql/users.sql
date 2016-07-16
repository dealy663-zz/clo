WITH id_row AS (
  INSERT INTO users
    (username, first_name, last_name, email)
  VALUES ('user_test', 'test', 'user', 'tuser@dd.com') RETURNING id
)
SELECT id FROM id_row



WITH id_row AS (
  INSERT INTO users
    (username, first_name, last_name, email)
  VALUES ("dealy", ", :last_name, :email) RETURNING id`
)
SELECT id FROM id_row

select * from users

delete from users