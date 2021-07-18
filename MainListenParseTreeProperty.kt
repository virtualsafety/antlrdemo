package antlrdemo

import com.huawei.calc.CalcLexer
import com.huawei.calc.CalcParser
import com.huawei.calc.CalcBaseListener
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.*

import java.util.*

import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeProperty
import java.util.HashMap



object MainListenParseTreeProperty {
    @Throws(Exception::class)
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
            println(parser.buildParseTree)
            val tree = parser.prog() // parse
            println(tree.toStringTree())

            val walker = ParseTreeWalker()
            val listener = Listener()

            walker.walk(listener, tree)
            println(tree.toStringTree(parser))
            println(listener.getValue(tree))
        }
    }
}


class Listener : CalcBaseListener() {
    var vars: MutableMap<String, Int> = HashMap()
    var values = ParseTreeProperty<Int>()

    // stmt : ID '=' expr NEWLINE ;
    override fun exitAssign(ctx: CalcParser.AssignContext) {
        val id = ctx.ID().text
        val `val` = values.get(ctx.expr())
        vars[id] = `val`
    }

    // stmt : expr NEWLINE ;
    override fun exitPrintExpr(ctx: CalcParser.PrintExprContext) {
        //println("result:" + values.get(ctx.expr()))
        values.put(ctx, values.get(ctx.expr()))
    }

    override fun exitProg(ctx: CalcParser.ProgContext) {
        values.put(ctx, values.get(ctx.getChild(0)))
    }

    // expr : ID ;
    override fun exitId(ctx: CalcParser.IdContext) {
        values.put(ctx, if (vars.containsKey(ctx.ID().text)) vars[ctx.ID().text] else 0)
    }

    // expr : expr op=('*'|'/') expr ;
    override fun exitMulDiv(ctx: CalcParser.MulDivContext) {
        val lhs = values.get(ctx.expr(0))
        val rhs = values.get(ctx.expr(1))
        values.put(ctx, if (ctx.op.type == CalcParser.MUL) lhs * rhs else lhs / rhs)
    }

    // expr : expr op=('+'|'-') expr ;
    override fun exitAddSub(ctx: CalcParser.AddSubContext) {
        val lhs = values.get(ctx.expr(0))
        val rhs = values.get(ctx.expr(1))
        //println(lhs)
        values.put(ctx, if (ctx.op.type == CalcParser.ADD) lhs + rhs else lhs - rhs)
    }

    // expr : '(' expr ')' ;
   override fun exitParens(ctx: CalcParser.ParensContext) {
        values.put(ctx, values.get(ctx.expr()))
    }

    override fun exitInt(ctx: CalcParser.IntContext) {
        values.put(ctx, ctx.INT().text.toInt())
    }

    fun getValue(node: ParseTree): Int {
        return values.get(node)
    }
}
