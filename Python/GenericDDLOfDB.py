#!/usr/local/bin/python3.6


# 用途：这个脚本是用来导出configList里面所有数据库DDL的；
# 使用场景：线上线下数据库排查不同字段，索引等
# 其他：需要结合ide的比对工具

import pymysql
import difflib
import sys
import filecmp
import os
configList = ({

'host':'',

'user':'',

'password':'',

'db':'',

'port': 3306

},{
'host':'',


'user':'',

'password':'',

'db':'',

'port': 
}
)
resultCollection = []
i = 0;
# 获取所有数据库的建表语句
for config in configList:
    # print(str(config))
    connection = pymysql.connect(config['host'], config['user'], config['password'],config['db'],config['port'])
    assert isinstance(connection, object)
    cursor = connection.cursor()
    cursor.execute("show tables")
    result = cursor.fetchall()
    adbResult = []
    with open("dbCreateSql" + str(i), 'w') as f:
        for table in result:
            cursor.execute("show create table " + str(table[0]))
            createTable = cursor.fetchall()
            createResult = ""
            for param in createTable:
                createResult = createResult + str(tuple(createTable[0])[1]) + "\n"
            adbResult.append(createResult)

        for astr in adbResult :
            for a in astr.split('\n'):
                f.write(a + '\n')
        f.flush()
    i = i + 1
    resultCollection.append(adbResult)
## resultCollection[0] 是第一个数据库检表语句，resultCollection[1]是第二个数据库建表语句
## resultController[0][1]是第一个数据库的第一个表的建表语句
