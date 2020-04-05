package com.jetbrains.internship.lightweight.parse.model

class BinaryExpression (
    val operation: BinaryOperation,
    val left: Expression,
    val right: Expression
) : Expression