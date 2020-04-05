package com.jetbrains.internship.lightweight.flatten

import com.jetbrains.internship.lightweight.infer.InferFail
import com.jetbrains.internship.lightweight.infer.inferCallChain
import com.jetbrains.internship.lightweight.model.*
import com.jetbrains.internship.lightweight.model.ConstantExpression.Companion.constExpr

fun flattenCallChain(callChain: CallChain, startType: Type = Type.NumType): CallChain? {
    if (inferCallChain(startType, callChain) is InferFail) {
        return null
    }

    val accumulator = Flattener()
    callChain.forEach(accumulator::addCall)
    return callChainOf(FilterCall(accumulator.buildFilter()), MapCall(accumulator.getMap()))
}

private class Flattener {
    private val filterExpressions: MutableList<Expression> = mutableListOf()
    private var mapExpression: Expression = Element()

    fun addCall(call: Call) {
        when (call) {
            is FilterCall -> filterExpressions.add(substitute(mapExpression, call.predicate))
            is MapCall -> mapExpression = substitute(mapExpression, call.transform)
        }
    }

    fun buildFilter(): Expression = filterExpressions.firstOrNull()?.let { head ->
        filterExpressions.drop(1).fold(head) { acc: Expression, curr: Expression ->
            BinaryExpression("&", acc, curr)
        }
    } ?: BinaryExpression("=", constExpr(1), constExpr(1))

    fun getMap(): Expression = mapExpression
}