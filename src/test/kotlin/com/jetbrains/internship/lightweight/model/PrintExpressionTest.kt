package com.jetbrains.internship.lightweight.model

import com.jetbrains.internship.lightweight.model.ConstantExpression.Companion.constExpr
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PrintExpressionTest {

    @Test
    fun `positive num`() {
        assertPrint("123", constExpr(123))
    }

    @Test
    fun `negative num`() {
        assertPrint("-123", constExpr(-123))
    }

    @Test
    fun bool() {
        assertPrint("true", constExpr(true))
        assertPrint("false", constExpr(false))
    }

    @Test
    fun `binary expression`() {
        assertPrint("(1+2)", BinaryExpression("+", constExpr(1), constExpr(2)))
        assertPrint("(1-2)", BinaryExpression("-", constExpr(1), constExpr(2)))
    }

    @Test
    fun `left nested expression`() {
        assertPrint("(1+(2+3))", BinaryExpression("+",
            constExpr(1),
            BinaryExpression("+",
                constExpr(2),
                constExpr(3)
            )
        ))
    }

    @Test
    fun `right nested expression`() {
        assertPrint("((1+2)+3)", BinaryExpression("+",
            BinaryExpression("+",
                constExpr(1),
                constExpr(2)
            ),
            constExpr(3)
        ))
    }

    @Test
    fun element() {
        assertPrint("element", Element())
    }

    private fun assertPrint(expected: String, expression: Expression) {
        assertEquals(expected, printExpression(expression))
    }
}