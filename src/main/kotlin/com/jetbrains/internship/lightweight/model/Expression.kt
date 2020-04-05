package com.jetbrains.internship.lightweight.model

interface Expression {
    fun <T> accept(visitor: ExpressionVisitor<T>): T
}

interface ExpressionVisitor<T> {
    fun visitElement(expression: Element): T
    fun visitConstantExpression(expression: ConstantExpression<*>): T
    fun visitBinaryExpression(expression: BinaryExpression): T
}