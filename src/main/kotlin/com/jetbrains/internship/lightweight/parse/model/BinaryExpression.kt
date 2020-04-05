package com.jetbrains.internship.lightweight.parse.model

class BinaryExpression (
    val operationSign: String,
    val left: Expression,
    val right: Expression
) : Expression