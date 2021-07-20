package antlrdemo

import org.antlr.v4.runtime.ParserRuleContext

import org.antlr.v4.runtime.TokenStreamRewriter
import org.antlr.v4.runtime.TokenStream;
import com.huawei.sqlparser.PostgreSQLStatementBaseListener
import com.huawei.sqlparser.PostgreSQLStatementParser


class SqlParserListener(tokens: TokenStream): PostgreSQLStatementBaseListener() {
    internal var rewriter: TokenStreamRewriter

    init {
        rewriter = TokenStreamRewriter(tokens)
    }

    override fun exitSimpleSelect(ctx: PostgreSQLStatementParser.SimpleSelectContext) {
        println("SimpleSelectContext: " + ctx.text)
    }

    override fun exitIdentifier(ctx: PostgreSQLStatementParser.IdentifierContext) {
        //replace the name of table
        rewriter.replace(ctx.start,ctx.stop,"table_replaced")
    }

    override fun exitFromClause(ctx: PostgreSQLStatementParser.FromClauseContext) {
        //replace "from" to "to"
        rewriter.replace(ctx.getStart(),"to")
    }

    override fun exitEveryRule(ctx: ParserRuleContext ) {
        println("test")
        println(ctx.javaClass)
    }
}
