CREATE INDEX ix_uqh_usrq
  ON public.user_question_history
  USING btree
  (user_id, question_id, query_time);
--;;

DROP INDEX public.fki_usrq_question;
--;;

CREATE INDEX ix_usrq_unq
  ON public.user_questions
  USING btree
  (user_id, question_id);
--;;