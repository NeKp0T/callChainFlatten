package com.jetbrains.internship.lightweight.model

data class BinaryExpression (
    val operationSign: String,
    val left: Expression,
    val right: Expression
) : Expression {
    override fun <T> accept(visitor: ExpressionVisitor<T>): T = visitor.visitBinaryExpression(this)
}