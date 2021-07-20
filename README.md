# antlrdemo
参考https://github.com/wojiaxiaoyueyue/Antlr4.7-Calculator


https://gist.github.com/virtualsafety/9d27a62ab615c9425d830fb35a4d5b76


(1)重点关注如何使用ParseTreeProperty

https://zhenglinj.github.io/technology/2017/08/05/antlr4-maven-examples/

https://github.com/tcoenraad/compiler-construction/blob/master/block-2/pp/block2/cc/antlr/Calculator.java

https://blog.csdn.net/qq_37771475/article/details/106546742

https://stuff.mit.edu/afs/athena/software/antlr_v4.5/code/listeners/TestLEvaluatorWithProps.java


(2)sqlparser for postgresql

https://github.com/pgcodekeeper/pgcodekeeper/tree/master/apgdiff/antlr-src

https://github.com/antlr/grammars-v4/issues/1501
https://github.com/apache/shardingsphere/blob/master/shardingsphere-sql-parser/shardingsphere-sql-parser-dialect/shardingsphere-sql-parser-postgresql/src/main/antlr4/imports/postgresql/Keyword.g4

把Keyword.g4中间中 
WS
    : [ \t\r\n] + ->skip
    ;
    
替换为
WS
    : [ \t\r\n] + -> channel(HIDDEN)
    ;

测试案例：
输入： select * from table1,table2;
输出： select *  to  table_replaced,table_replaced;


测试代码：
SqlParserMain.kt
SqlParserListener.kt


