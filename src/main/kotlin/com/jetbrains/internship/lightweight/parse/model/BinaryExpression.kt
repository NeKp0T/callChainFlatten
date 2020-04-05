package com.jetbrains.internship.lightweight.parse.model

class BinaryExpression<T : Value<*>, U : Value<*>>(
    val operation: BinaryOperation<T, U>,
    val left: Expression<T>,
    val right: Expression<T>
) : Expression<U>