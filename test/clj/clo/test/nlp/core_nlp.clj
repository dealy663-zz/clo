(ns clo.test.nlp.core-nlp
  (:require [clojure.test :refer :all]
            [luminus-migrations.core :as migrations]
            [clo.config :refer [env]]
            [mount.core :as mount]
            [clojure.tools.logging :as log])
  (:import (java.util Properties)
           (edu.stanford.nlp.pipeline StanfordCoreNLP RegexNERAnnotator TokensRegexAnnotator Annotation)
           (java.text SimpleDateFormat)
           (edu.stanford.nlp.ling CoreAnnotations CoreAnnotations$DocDateAnnotation CoreAnnotations$SentencesAnnotation CoreAnnotations$TextAnnotation CoreAnnotations$StackedNamedEntityTagAnnotation CoreAnnotations$PartOfSpeechAnnotation CoreAnnotations$LemmaAnnotation CoreAnnotations$TokensAnnotation)
           (edu.stanford.nlp.semgraph SemanticGraphCoreAnnotations$CollapsedDependenciesAnnotation)))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'clo.config/env
      #'clo.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(def props (Properties.))
(.put props  "annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, dcoref")
(def pipeline (StanfordCoreNLP. props))

;; These should be explained later in the tutorial
;(.addAnnotator pipeline
;               (RegexNERAnnotator. "some RegexNer structured file"))
;(.addAnnotator pipeline
;               (TokensRegexAnnotator. “some tokenRegex structured file”))

(def formatter (SimpleDateFormat. "yyyy-MM-dd"))
(def currentTime (.format formatter (System/currentTimeMillis)))
(def inputText "some text to evaluate")
(def document (Annotation. inputText))
(.set document CoreAnnotations$DocDateAnnotation currentTime)
(.annotate pipeline document)
(def sentences (.get document CoreAnnotations$SentencesAnnotation))

(defn show-tokens
  [token]
  (let [text  (.getString token CoreAnnotations$TextAnnotation)
        ner   (.getString token CoreAnnotations$StackedNamedEntityTagAnnotation)
        pos   (.get token CoreAnnotations$PartOfSpeechAnnotation)
        lemma (.get token CoreAnnotations$LemmaAnnotation)]
    (log/debug (str "text=" text "; NER=" ner "; POS=" pos "; LEMMA=" lemma))))

(defn show-edge-info
  [edge]
  (let [dep (.getDependent edge)
        gov (.getGovernor edge)
        rel (.getRelation edge)]
    (log/debug (str "Dependent=" dep))
    (log/debug (str "Governor=" gov))
    (log/debug (str "Relation=" rel))))

(defn get-annotations
  [sentence]
  (doall (map show-tokens (.get sentence CoreAnnotations$TokensAnnotation)))
  (let [dependencies  (.get sentence SemanticGraphCoreAnnotations$CollapsedDependenciesAnnotation)
        first-root    (.getFirstRoot dependencies)]
        (doall (map show-edge-info (.getOutEdgesSorted dependencies first-root)))))

(doall (map get-annotations sentences))