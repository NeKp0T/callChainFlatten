package com.jetbrains.internship.lightweight.straighten

import com.jetbrains.internship.lightweight.model.*

fun substitute(substitution: Expression, expression: Expression) = expression.accept(SubsVisitor(substitution))

private class SubsVisitor(private val elementValue: Expression) : ExpressionVisitor<Expression> {
    override fun visitElement(expression: Element): Expression = elementValue

    override fun visitConstantExpression(expression: ConstantExpression<*>): Expression = expression

    override fun visitBinaryExpression(expression: BinaryExpression): Expression = expression.copy(
            left = expression.left.accept(this),
            right = expression.right.accept(this)
        )
}