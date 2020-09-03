# Topic Branch java-libs-gson

The Google librairy for JSON Object mapping seems to be rather fast and a little bit
more than 200 KByte rather small compared with Jackson. Furthermore the number
of methods looks overseeable.

## Targets
My target for this sandbox is to check smaller alternatives
 to Jackson. From the size view point with my rather simple
comparable speed for mapping json files to java objects, since
I only use 1% of all the stuff gson is offering.

## Performance
With the small performance check I had the following results in ms. With 5 million
objects we have a deserialize performance between 3 and 13 s. 

### gson 2.3.1
|max|listSerialize|untypedList|typedList|mapSerialize|untypedMap|typedMap|
|----:|----:|----:|----:|----:|----:|----:|
|100000|195|150|140|188|250|179|
|1000000|882|609|400|1642|551|536|
|5000000|4640|6828|3609|6078|8816|3495|

### gson 2.8.4
|max|listSerialize|untypedList|typedList|mapSerialize|untypedMap|typedMap|
|----:|----:|----:|----:|----:|----:|----:|
|100000|162|88|96|173|130|83|
|1000000|660|1290|359|806|888|572|
|5000000|2724|13578|3059|3259|9554|2699|

## Links
* https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.2
* https://sites.google.com/site/gson/gson-user-guide
* http://tutorials.jenkov.com/java-json/index.html
* http://www.javacreed.com/simple-gson-example/
### Stuff to look at
* https://futurestud.io/tutorials/gson-advanced-custom-serialization-part-1
### Ordered fields not implemented :-(
* https://github.com/google/gson/issues/466
* https://github.com/google/gson/issues/45
* https://stackoverflow.com/questions/30030601/how-to-create-json-sorted-on-keys-using-gson

