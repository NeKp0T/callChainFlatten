package com.jetbrains.internship.lightweight.infer

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.jetbrains.internship.lightweight.model.Type
import com.jetbrains.internship.lightweight.model.Type.BoolType
import com.jetbrains.internship.lightweight.model.Type.NumType
import com.jetbrains.internship.lightweight.parse.ExprGrammar
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CallChainInferTest {

    @ParameterizedTest
    @MethodSource("correctExpressionsSource")
    fun `infers call chain`(expression: String, startType: Type, resultType: Type) {
        when (val inferred = parseInfer(startType, expression)) {
            is InferFail -> fail("Infer failed at: ${inferred.where}")
            is Inferred -> Assertions.assertEquals(resultType, inferred.type)
        }
    }


    @ParameterizedTest
    @MethodSource("failingExpressionsSource")
    fun `infer call chain fails`(expression: String, startType: Type) {
        when (parseInfer(startType, expression)) {
            is InferFail -> return
            is Inferred -> fail("Inferred type of uninferrable expression: $expression")
        }
    }

    private fun parseInfer(elementType: Type, input: String): InferResult = inferCallChain(
        elementType,
        ExprGrammar.parseToEnd(input)
    )

    companion object {
        @JvmStatic
        fun correctExpressionsSource() = Stream.of(
            Arguments.of("map { element }", NumType, NumType),
            Arguments.of("map { element }", BoolType, BoolType),
            Arguments.of("map { (element + element) }", NumType, NumType),
            Arguments.of("map { (element > 0) }", NumType, BoolType),
            Arguments.of("filter { element } ", BoolType, BoolType),
            Arguments.of("filter { (element > 0) }", NumType, NumType),
            Arguments.of("filter { ((element + 1) > 0) }", NumType, NumType),
            Arguments.of("filter { (element > 0) } %>% map { (element + 1) }", NumType, NumType),
            Arguments.of("map { (element > 0) } %>% filter { element }", NumType, BoolType)
        )

        @JvmStatic
        fun failingExpressionsSource() = Stream.of(
            Arguments.of("map { (element > 0) } %>% filter { (element < 0)  }", NumType),
            Arguments.of("map { (element + 1) } %>% filter { (element & (1 = 1))  }", NumType)
        )
    }
}