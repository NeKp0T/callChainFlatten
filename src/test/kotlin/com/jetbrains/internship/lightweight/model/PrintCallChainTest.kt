package com.jetbrains.internship.lightweight.model

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.jetbrains.internship.lightweight.parse.ExprGrammar
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PrintCallChainTest {

    @ParameterizedTest
    @ValueSource(strings = [
        "filter { element }",
        "map { element } %>% filter { element }",
        "map { (1 + 1) }",
        "map { element } %>% filter { element } %>% filter { 1 }",
        "filter { (element + 1) }"
    ])
    fun `call chain is printed correctly`(chain: String) {
        assertEquals(
            chain.replace("[\\s]".toRegex(), ""),
            printCallChain(ExprGrammar.parseToEnd(chain))
        )
    }
}