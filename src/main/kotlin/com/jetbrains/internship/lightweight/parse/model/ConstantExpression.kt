package com.jetbrains.internship.lightweight.parse.model

class ConstantExpression<T : Value<*>>(val value: T) : Expression<T>