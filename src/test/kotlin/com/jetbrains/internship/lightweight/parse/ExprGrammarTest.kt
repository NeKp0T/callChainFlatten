package com.jetbrains.internship.lightweight.parse

import com.github.h0tk3y.betterParse.parser.*
import com.jetbrains.internship.lightweight.model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ExprGrammarTest {

    @Test
    fun `positive number constant`() {
        assertEquals(constExpr(123), parseToEnd("123", ExprGrammar.constantExpression))
    }

    @Test
    fun `negative number constant`() {
        assertEquals(constExpr(-123), parseToEnd("-123", ExprGrammar.constantExpression))
    }

    @Test
    fun element() {
        assertEquals(Element(), parseToEnd("element", ExprGrammar.element))
    }

    @Test
    fun `binary expression`() {
        assertEquals(
            BinaryExpression("+", constExpr(123), constExpr(321)),
            parseToEnd("(123+321)", ExprGrammar.binaryExpression)
        )
    }

    @Test
    fun `binary expression with element`() {
        assertEquals(
            BinaryExpression("+", constExpr(123), Element()),
            parseToEnd("(123 + element)", ExprGrammar.binaryExpression)
        )
    }

    @Test
    fun `nested binary expressions`() {
        assertEquals(
            BinaryExpression("+", constExpr(1), BinaryExpression("-", constExpr(2), constExpr(3))),
            parseToEnd("(1 + (2 - 3))", ExprGrammar.binaryExpression)
        )
    }

    @Test
    fun `wrongly typed nested expressions`() {
        assertEquals(
            BinaryExpression("+", constExpr(1), BinaryExpression("=", constExpr(2), constExpr(3))),
            parseToEnd("(1 + (2 = 3))", ExprGrammar.binaryExpression)
        )
    }

    @Test
    fun `map call`() {
        assertEquals(
            MapCall(constExpr(1)),
            parseToEnd("map { 1 }", ExprGrammar.mapCall)
        )
    }

    @Test
    fun `filter call`() {
        assertEquals(
            FilterCall(constExpr(1)),
            parseToEnd("filter { 1 }", ExprGrammar.filterCall)
        )
    }

    @Test
    fun `call chain of one call`() {
        assertEquals(
            listOf(MapCall(constExpr(1))),
            parseToEnd("map { 1 }", ExprGrammar.callChain)
        )
    }

    @Test
    fun `call chain of multiple calls`() {
        assertEquals(
            listOf(MapCall(constExpr(1)), FilterCall(constExpr(2)), FilterCall(constExpr(3))),
            parseToEnd("map { 1 } %>% filter { 2 } %>% filter { 3 }", ExprGrammar.callChain)
        )
    }

    @Test
    fun `empty call chain fails`() {
        assertThrows(ParseException::class.java) { parseToEnd("", ExprGrammar.callChain) }
    }

    private fun <T> tryParseToEnd(input: String, parser: Parser<T>): ParseResult<T> {
        val tokens = ExprGrammar.tokenizer.tokenize(input)
        return parser.tryParseToEnd(tokens)
    }

    private fun <T> parseToEnd(input: String, parser: Parser<T>): T = tryParseToEnd(input, parser).toParsedOrThrow().value

    private fun constExpr(value: Int) = ConstantExpression(Num(value))
}