## gso
A Clojure implementation of the Glowworm Swarm Optimization
algorithm as described in the original paper from:
<br><br>
Krishnanand, K.N. and Ghose, D.,
"Glowworm swarm optimization for simultaneous capture of multiple local optima
of multimodal functions", published in Swarm Intelligence,
3, 2, June 2009, 87-124. (http://dl.acm.org/citation.cfm?id=1542054)
<br>

This project uses [Midje](https://github.com/marick/Midje/).

### How to run the tests

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.