# cp-investigator

A Clojure library designed to test the differences between hadoop and java clt with regards to standard java class path and uberjar tactics.

## Usage

```
lein uberjar
```

in hadoop:

```
hadoop jar <your-uberjar> cp_investigator.core
```

in java:

```
java -cp <your-uberjar> clojure.main --main cp-investigator.core
```

Running the above, you'll note that the class path adn uberjar found whlie in the hadoop env show a tmp uberjar. The subprocess cannot run because that jar does not exist to anything but the currently running hadoop command (speculation). However, the java command runs just fine. This project was developed because I was trying to write some code which would actually use java processes to run the jar that was currently running, and ran into the oddity of not being able to find the jar itself. Instead of spend long wait times on that to uberjar, we ended up with this.

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
