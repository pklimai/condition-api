# BM@N Condition Database API

### Search database

You can provide parameter values for filtering. 
String-type parameters support exact match. 
Number-type support exact match, greater-or-equal (e.g. `10+`), less-or-equal (e.g. `10-`) 
and inclusive ranges (e.g. `10-15`). 

Search or filtering is supported for all fields, except: `file_path`, `start_datetime`, `end_datetime`, `file_md5`.

### Example calls

Example with range and strings match:
```
/unidb-api/v1/runs?run_number=3950-4000&beam_particle=Ar&target_particle=Al
```

Spaces in search string should be replaced with `%20`, e.g.:
```
http://127.0.0.1:8080/unidb-api/v1/runs?energy=3.16-3.18&target_particle=SRC%20Lead%201
```
(note also, that for floating point parameters we suggest to *not* use exact match).

