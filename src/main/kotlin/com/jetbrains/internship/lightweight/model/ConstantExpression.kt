package com.jetbrains.internship.lightweight.model

data class ConstantExpression<T : Value<*>>(val value: T) : Expression {
    override fun <T> accept(visitor: ExpressionVisitor<T>): T = visitor.visitConstantExpression(this)
}