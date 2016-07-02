(ns clo.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [clo.core-test]))

(doo-tests 'clo.core-test)

