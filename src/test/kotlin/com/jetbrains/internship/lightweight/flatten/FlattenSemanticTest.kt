package com.jetbrains.internship.lightweight.flatten

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.jetbrains.internship.lightweight.model.Type
import com.jetbrains.internship.lightweight.model.Type.*
import com.jetbrains.internship.lightweight.parse.ExprGrammar
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FlattenSemanticTest {

    @Test
    fun `doesn't change already good solution`() {
        checkFlattenDoesNothing("(element = 2)", "(element + 1)")
    }

    @Test
    fun `doesn't change already good solution with bool input`() {
        checkFlattenDoesNothing("(element = (1 = 1))", "(element = (0 = 1))", BoolType)
    }

    @Test
    fun `flip map and filter`() {
        checkFlatten(
            "((element + 1) > 0)",
            "(element + 1)",
            "map { (element + 1) } %>% filter { (element > 0) }"
        )
    }

    @Test
    fun `no filter`() {
        checkFlatten(
            "(1 = 1)",
            "(element + 1)",
            "map { (element + 1) }"
        )
    }

    @Test
    fun `no map`() {
        checkFlatten(
            "(element > 1)",
            "element",
            "filter { (element > 1) }"
        )
    }

    @Test
    fun `fails if wrong types for call`() {
        checkFlattenFails("filter { (element + 1) }")
    }

    @Test
    fun `fails if wrong types in call`() {
        checkFlattenFails("filter { ((0 = 1) = 2) }")
    }

    @Test
    fun `multiple filters`() {
        checkFlatten(
            "((element = 1) & (element = 2))",
            "element",
            "filter { (element = 1) } %>% filter { (element = 2) }"
        )
    }

    @Test
    fun `multiple filters with a map`() {
        checkFlatten(
            "((element = 1) & ((element + 1) = 2))",
            "(element + 1)",
            "filter { (element = 1) } %>% map { (element + 1) } %>% filter { (element = 2) }"
        )
    }

    private fun checkFlattenDoesNothing(
        predicate: String,
        transform: String,
        startType: Type = NumType
    ) = checkFlatten(predicate, transform, "filter {$predicate} %>% map {$transform}", startType)

    private fun checkFlatten(expectedPredicate: String, expectedTransform: String, chain: String, startType: Type = NumType) {
        assertEquals(
            ExprGrammar.parseToEnd("filter { $expectedPredicate } %>% map { $expectedTransform }"),
            flattenCallChain(
                ExprGrammar.parseToEnd(chain),
                startType
            )
        )
    }

    private fun checkFlattenFails(chain: String, startType: Type = NumType) {
        assertNull(flattenCallChain(ExprGrammar.parseToEnd(chain), startType))
    }

}