CREATE SCHEMA IF NOT EXISTS annapurna;

CREATE TABLE annapurna.anna
(
  user_ref bigint NOT NULL,
  anamorph text,
  reset_date timestamp with time zone,
  valid boolean,
  CONSTRAINT anna_pkey PRIMARY KEY (user_ref),
  CONSTRAINT fk_anna_user FOREIGN KEY (user_ref)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE annapurna.polyanna
(
  user_ref bigint NOT NULL,
  history_val integer,
  anamorph text,
  CONSTRAINT polyanna_pkey PRIMARY KEY (user_ref),
  CONSTRAINT fk_polyanna_user FOREIGN KEY (user_ref)
      REFERENCES annapurna.anna (user_ref) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

