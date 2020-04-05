package com.jetbrains.internship.lightweight.infer

import com.jetbrains.internship.lightweight.model.*

sealed class InferResult
data class Inferred(val type: Type) : InferResult()
class InferFail(val where: Expression) : InferResult()

fun infer(elementType: Type, expression: Expression): InferResult = expression.accept(InferVisitor(elementType))

private class InferVisitor(private val elementType: Type) : ExpressionVisitor<InferResult> {
    override fun visitElement(expression: Element): InferResult = Inferred(elementType)

    override fun visitConstantExpression(expression: ConstantExpression<*>): InferResult = Inferred(expression.value.type)

    override fun visitBinaryExpression(expression: BinaryExpression): InferResult {
        return when (val leftResult = expression.left.accept(this)) {
            is InferFail -> leftResult
            is Inferred -> when (val rightResult = expression.right.accept(this)) {
                is InferFail -> rightResult
                is Inferred -> {
                    val leftType = leftResult.type
                    val rightType = rightResult.type
                    operationResultType(
                        expression.operationSign,
                        leftType,
                        rightType
                    )?.let(::Inferred) ?: InferFail(expression)
                }
            }
        }
    }
}