# sysj_template
SystemJ command line environment

## usage
1. put `.sysj` and `.xml` file into `/source_file`
2. make
3. make run
4. make clean

### for specific sysj
use `SYSJ=` when compile

e.g
```shell
make SYSJ=Exercise1

make run SYSJ=Exercise1

make clean
```

### how to add library
copy `.jar` libraries to `./sysj/lib/`

### how to add 
copy `.java` files to `./source_file/src`
