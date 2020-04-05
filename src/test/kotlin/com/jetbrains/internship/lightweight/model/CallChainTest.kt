package com.jetbrains.internship.lightweight.model

import com.jetbrains.internship.lightweight.model.ConstantExpression.Companion.constExpr
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CallChainTest {

    @Test
    fun `iterator test`() {
        val calls = listOf(FilterCall(Element()), MapCall(Element()), FilterCall(constExpr(123)))
        val chain = callChainOf(calls.first(), *calls.drop(1).toTypedArray())
        assertEquals(calls, chain.toList())
    }
}