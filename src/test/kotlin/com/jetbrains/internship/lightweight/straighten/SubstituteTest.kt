package com.jetbrains.internship.lightweight.straighten

import com.jetbrains.internship.lightweight.model.printExpression
import com.jetbrains.internship.lightweight.parse.ExprGrammar
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class SubstituteTest {

    @ParameterizedTest
    @ValueSource(strings = ["123", "-321", "(1+2)", "((1=2)&(2=2))"])
    fun `substitute single element`(expression: String) {
        assertSubstitute(expression, "element")
    }

    @ParameterizedTest
    @CsvSource(value = [
        "(element+1),123",
        "(element+element),123",
        "(element+element),(1+2)",
        "(1+(1+element)),123",
        "((1+element)+2),123"
    ])
    fun `complex substitutions`(expression: String, substitution: String) {
        assertSubstitute(substitution, expression)
    }

    private fun assertSubstitute(substitution: String, expression: String) {
        val result = substitute(
            ExprGrammar.parseExpressionToEnd(substitution),
            ExprGrammar.parseExpressionToEnd(expression)
        )
        val expected = expression.replace("element", substitution)
        assertEquals(expected, printExpression(result))
    }
}