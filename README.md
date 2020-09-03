# Sandbox Java Librairies

This sandbox repository uses branches to gather examples and topics of interest
different librairies. So this master is rather empty.

If you are interested in one available topic just
go to the adequate branch.

If you cloned this repository you can just checkout e.g.

> git checkout cli:jewelcli

## Libraries
### Json
A first focus is to evaluate different json libraries. Topics are
* size of the jar
* simple serialize and deserialize methods
* ordered serializing possible
* speed

#### focused
* [fastjson](https://github.com/fluentcodes/sandbox-java-libs/tree/json.fastjson): A small, fast and straightforward library.
* [genson](https://github.com/fluentcodes/sandbox-java-libs/tree/json.genson): A small, fast and straightforward library.
* [gson](https://github.com/fluentcodes/sandbox-java-libs/tree/json.gson): The google json librairy is rather small and fast with streaming capabilities.
* [jackson](https://github.com/fluentcodes/sandbox-java-libs/tree/json.jackson): With spring integration a de facto standard for json. Rather huge with some vulnerabilities.

#### Out of focus
Some libraries have a different focus than just simply serialize/deserialize or/and
lacks the capabilities to implement the test scenario. They could be valueable for
different needs e.g. filtering/manipulation json data.

* [boon](https://github.com/fluentcodes/sandbox-java-libs/tree/json.boon):
* [json-io](https://github.com/fluentcodes/sandbox-java-libs/tree/json.json-io): With a special philosophy read and write json with mapping information. Some problems running the standard tests.
* [jsonsurfer](/https://github.com/fluentcodes/sandbox-java-libs/tree/json.jsonsurfer):
* [jsonpath](https://github.com/fluentcodes/sandbox-java-libs/tree/json.jsonoath):

#### Links
* http://json.org/

### Other
* [jewelcli](https://github.com/fluentcodes/sandbox-java-libs/tree/cli.jewelcli)

## targets
The target of a sandbox is
* gather examples working out of the box
* evaluate different aspects like speed

It's not intended to
* cover every aspect
* be a complete tutorial
* be a book

As the nature of the sandbox is a repository programmers could collaborate
to cover topics deeper and distribute their experience in a way other
can use their experience without drawback creating rather complex environments.


## Links
* http://ronnieroller.com/java - a comprehensiv list of java librairies
* https://spring.io/blog/2015/02/23/better-dependency-management-for-gradle
* https://fluentcodes.com/sandboxes

----
[sandbox](http://fluentcodes.com/sandboxes) [wiki](https://en.wikipedia.org/wiki/Wiki) page with  [markdown](https://en.wikipedia.org/wiki/Markdown)  by [fluentcodes](https://fluentcodes.com)

