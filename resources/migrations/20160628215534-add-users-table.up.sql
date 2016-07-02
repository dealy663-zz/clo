CREATE TABLE public.users
(
  id BIGSERIAL NOT NULL,
  username character varying(20) NOT NULL,
  first_name character varying(30),
  last_name character varying(30),
  email character varying(30),
  admin boolean,
  last_login time without time zone,
  is_active boolean,
  CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE INDEX ix_uname
  ON public.users
  USING btree
  (username COLLATE pg_catalog."default");

