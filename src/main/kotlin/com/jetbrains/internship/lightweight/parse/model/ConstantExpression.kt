package com.jetbrains.internship.lightweight.parse.model

data class ConstantExpression<T : Value<*>>(val value: T) : Expression