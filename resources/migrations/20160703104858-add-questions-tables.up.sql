CREATE TABLE public.question
(
  id bigserial,
  query text,
  CONSTRAINT pk_question PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE UNIQUE INDEX ix_question
  ON public.question
  USING btree
  (query COLLATE pg_catalog."default");
--;;

CREATE TABLE public.user_questions
(
  user_id bigint NOT NULL,
  question_id bigint NOT NULL,
  CONSTRAINT pk_user_question PRIMARY KEY (user_id),
  CONSTRAINT fk_usrq_question FOREIGN KEY (question_id)
      REFERENCES public.question (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usrq_usr FOREIGN KEY (user_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX fki_usrq_question
  ON public.user_questions
  USING btree
  (question_id);
--;;

CREATE TABLE public.user_question_history
(
  user_id bigint NOT NULL,
  question_id bigint NOT NULL,
  query_time timestamp with time zone,
  CONSTRAINT pk_usrq_history PRIMARY KEY (user_id),
  CONSTRAINT fk_usrqh_question FOREIGN KEY (question_id)
      REFERENCES public.question (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usrqh_user FOREIGN KEY (user_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX fki_usrqh_question
  ON public.user_question_history
  USING btree
  (question_id);
--;;