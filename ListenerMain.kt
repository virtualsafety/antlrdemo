package antlrdemo


import com.huawei.calc.CalcLexer
import com.huawei.calc.CalcParser
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.huawei.calc.CalculationListener
import java.util.Scanner;

object ListenerMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val `in` = Scanner(System.`in`)

        while (true) {
            print("Calculate: ")
            val line = `in`.nextLine()
            if (line.toLowerCase() == "exit" || line.isEmpty()) {
                break
            }
            val lineStream = CharStreams.fromString(line)

            val lexer = CalcLexer(lineStream)
            val tokens = CommonTokenStream(lexer)
            val parser = CalcParser(tokens)
            val tree = parser.prog()

            val calculator = CalculationListener()
            val walker = ParseTreeWalker()
            walker.walk(calculator, tree)

            println(tree.toStringTree(parser))
        }
    }
}
