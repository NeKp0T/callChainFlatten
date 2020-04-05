package com.jetbrains.internship.lightweight.parse

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.times
import com.github.h0tk3y.betterParse.combinators.use
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.parser.Parser
import com.jetbrains.internship.lightweight.parse.model.ConstantExpression
import com.jetbrains.internship.lightweight.parse.model.Expression
import com.jetbrains.internship.lightweight.parse.model.Num

object ExprGrammar : Grammar<Expression<*>>() {
    val ws by token("\\s+", ignore = true) // let's ignore whitespaces for the sake of readability
    val lpar by token("\\(")
    val rpar by token("\\)")
    val minusSign by token("-")
    val plusSign by token("\\+")
    val multSign by token("\\*")
    val compareSign by token("[><]")
    val boolOpSign by token("[|&]")
    val number by token("[\\d]+") use { text.toInt() }

    val numConstant by (number or (minusSign * number use { -t2 })) map ::Num
    val numBinaryExpression by
            lpar * () * rpar



    val numExpression by numConstant map ::ConstantExpression
//    val boolExpression by


    override val rootParser: Parser<Expression<*>> = TODO("Not yet implemented")
}