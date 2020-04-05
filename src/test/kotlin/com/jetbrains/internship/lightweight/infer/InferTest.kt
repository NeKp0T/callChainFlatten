package com.jetbrains.internship.lightweight.infer

import com.github.h0tk3y.betterParse.parser.parseToEnd
import com.jetbrains.internship.lightweight.model.Type
import com.jetbrains.internship.lightweight.model.Type.BoolType
import com.jetbrains.internship.lightweight.model.Type.NumType
import com.jetbrains.internship.lightweight.parse.ExprGrammar
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

internal class InferTest {

    @Test
    fun `number infer succeeds`() {
        assertInfer(NumType, "123")
        assertInfer(NumType, "-123")
    }

    @Test
    fun `number binary expr infer succeeds`() {
        assertInfer(NumType, "(1 + 2)")
        assertInfer(NumType, "(1 - 2)")
        assertInfer(NumType, "(1 * 2)")
        assertInfer(BoolType, "(1 = 2)")
        assertInfer(BoolType, "(1 > 2)")
        assertInfer(BoolType, "(1 < 2)")
    }

    @Test
    fun `nested num expressions infer succeeds`() {
        assertInfer(NumType, "(1 + (2 + 3))")
        assertInfer(NumType, "(1 * (2 - 3))")
    }

    @Test
    fun `bool binary expr infer succeeds`() {
        assertInfer(BoolType, "($trueExpr & $falseExpr)")
        assertInfer(BoolType, "($trueExpr | $falseExpr)")
        assertInfer(BoolType, "($trueExpr = $falseExpr)")
    }

    @Test
    fun `nested bool in num expressions infer fails`() {
        assertFails("(1 + (2 = 3))")
    }

    @Test
    fun `nested num in bool expressions infer fails`() {
        assertFails("($trueExpr & (2 + 3))")
    }

    @Test
    fun `equals with different types infer fails`() {
        assertFails("(1 = (2 = 3))")
    }

    @Test
    fun `num expr with element succeeds`() {
        assertInfer(NumType, "(1 + element)", NumType)
    }

    @Test
    fun `num expr with bool element fails`() {
        assertFails("(1 + element)", BoolType)
    }

    @Test
    fun `bool expr with element succeeds`() {
        assertInfer(BoolType, "($trueExpr & element)", BoolType)
    }

    @Test
    fun `bool expr with num element fails`() {
        assertFails("($trueExpr & element)", NumType)
    }

    private fun parseInfer(elementType: Type, input: String): InferResult = infer(
        elementType,
        ExprGrammar.expression.parseToEnd(ExprGrammar.tokenizer.tokenize(input))
    )

    private fun assertInfer(expectedType: Type, input: String, elementType: Type = NumType) {
        when (val inferred = parseInfer(elementType, input)) {
            is InferFail -> fail("Infer failed at: ${inferred.where}")
            is Inferred -> assertEquals(expectedType, inferred.type)
        }
    }

    private fun assertFails(input: String, elementType: Type = NumType) {
        when (parseInfer(elementType, input)) {
            is InferFail -> return
            is Inferred -> fail("Inferred type of uninferrable expression: $input")
        }
    }

    private val trueExpr = "(0 = 0)"
    private val falseExpr = "(0 = 1)"
}