(defproject gso "0.0.1-SNAPSHOT"
  :description "Cool new project to do things and stuff"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [dire "0.5.3"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"])