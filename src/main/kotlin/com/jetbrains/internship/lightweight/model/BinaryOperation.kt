package com.jetbrains.internship.lightweight.model


class BinaryOperation(val symbol: String, val inType: Type, val outType: Type)

fun getNumberBiOperation(symbol: String): BinaryOperation? {
    return if (listOf("+", "-", "*").contains(symbol))
        BinaryOperation(symbol, Type.NumType, Type.NumType)
    else
        null
}

fun getNumberBoolBiOperation(symbol: String): BinaryOperation? {
    return if (listOf(">", "<", "=").contains(symbol))
        BinaryOperation(symbol, Type.NumType, Type.BoolType)
    else null
}

fun getBoolBiOperation(symbol: String): BinaryOperation? {
    return if (listOf("&", "|", "=").contains(symbol))
        BinaryOperation(symbol, Type.BoolType, Type.BoolType)
    else null
}