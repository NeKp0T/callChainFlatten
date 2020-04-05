package com.jetbrains.internship.lightweight.model

import com.jetbrains.internship.lightweight.model.Type.*

fun operationResultType(symbol: String, leftType: Type, rightType: Type): Type? {
    val typeMap = mapOf(
        NumType to mapOf(
            NumType to mapOf(
                "+" to NumType,
                "-" to NumType,
                "*" to NumType,
                "<" to BoolType,
                ">" to BoolType,
                "=" to BoolType
            )
        ),
        BoolType to mapOf(
            BoolType to mapOf(
                "|" to BoolType,
                "&" to BoolType,
                "=" to BoolType
            )
        )
    )
    return typeMap[leftType]?.get(rightType)?.get(symbol)
}