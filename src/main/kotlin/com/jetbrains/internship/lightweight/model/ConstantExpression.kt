package com.jetbrains.internship.lightweight.model

data class ConstantExpression<T : Value<*>>(val value: T) : Expression {
    override fun <T> accept(visitor: ExpressionVisitor<T>): T = visitor.visitConstantExpression(this)

    companion object {
        fun constExpr(value: Int) = ConstantExpression(Num(value))
        fun constExpr(value: Boolean) = ConstantExpression(Bool(value))
    }
}

