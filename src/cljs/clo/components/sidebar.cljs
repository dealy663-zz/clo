(ns clo.components.sidebar
  (:require [reagent.core :as r]))

(defn sidebar []
  [:div#sidebar-wrapper
   [:ul.sidebar-nav
    [:li.sidebar-brand>a {:href "#"} "Clo Control"]
    [:li>a {:href "#"} "Page 1"]
    [:li>a {:href "#"} "Page 2"]
    [:li>a {:href "#"} "Page 3"]]])

(defn menu-toggle-render []
  (let [lbl (r/atom "<<")]
    (fn []
      [:div.btn.btn-default
       {:on-click #(if (= "<<" @lbl)
                     (reset! lbl ">>")
                     (reset! lbl "<<"))}
       @lbl])))

(defn menu-toggle-did-mount [this]
  (.click (js/$ (r/dom-node this))
          (fn [e]
            (.preventDefault e)
            (.toggleClass (js/$ "#wrapper") "toggled") ;#wrapper will be the id of a div in our home component
            )))

(defn menu-toggle []
  (r/create-class {:reagent-render      menu-toggle-render
                   :component-did-mount menu-toggle-did-mount}))