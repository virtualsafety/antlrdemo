package antlrdemo

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import com.huawei.sqlparser.PostgreSQLStatementParser
import com.huawei.sqlparser.PostgreSQLStatementLexer

import java.util.Scanner;

object SqlParserMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val `in` = Scanner(System.`in`)

        while (true) {
            print("sql text: ")
            val line = `in`.nextLine()
            if (line.toLowerCase() == "exit" || line.isEmpty()) {
                break
            }
            val lineStream = CharStreams.fromString(line)

            val lexer = PostgreSQLStatementLexer(lineStream)
            val tokens = CommonTokenStream(lexer)

            val parser = PostgreSQLStatementParser(tokens)
            val tree = parser.execute()
            println(tree.toStringTree())

            val sqlparser = SqlParserListener(tokens)
            val walker = ParseTreeWalker()
            walker.walk(sqlparser, tree)

            println(tree.toStringTree(parser))
            println(sqlparser.rewriter.getText())
        }
    }
}
