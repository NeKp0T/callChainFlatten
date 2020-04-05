package com.jetbrains.internship.lightweight.parse.model


interface BinaryOperation<in T : Value<*>, out U : Value<*>> {
    fun evaluate(left: T, right: T): U
    val symbol: String
}

private class BinaryOperationImpl<in T : Value<*>, out U : Value<*>>(override val symbol: String, val body: (T, T) -> U) : BinaryOperation<T, U> {
    override fun evaluate(left: T, right: T): U = body(left, right)
}

fun getNumberBiOperation(symbol: String): BinaryOperation<Num, Num>? {
    val operation = when (symbol) {
        "+" -> { a: Int, b: Int -> a + b }
        "-" -> { a: Int, b: Int -> a - b }
        "*" -> { a: Int, b: Int -> a * b }
        else -> null
    } ?: return null
    return BinaryOperationImpl(symbol, liftFromNumber(liftToNumber(operation)))
}

fun getNumberBoolBiOperation(symbol: String): BinaryOperation<Num, Bool>? {
    val operation = when (symbol) {
        ">" -> { a: Int, b: Int -> a > b }
        "<" -> { a: Int, b: Int -> a < b }
        "=" -> { a: Int, b: Int -> a == b }
        else -> null
    } ?: return null
    return BinaryOperationImpl(symbol, liftFromNumber(liftToBool(operation)))
}

fun getBoolBiOperation(symbol: String): BinaryOperation<Bool, Bool>? {
    val operation = when (symbol) {
        "&" -> { a: Boolean, b: Boolean -> a && b }
        "|" -> { a: Boolean, b: Boolean -> a || b }
        "=" -> { a: Boolean, b: Boolean -> a == b }
        else -> null
    } ?: return null
    return BinaryOperationImpl(symbol, liftFromBool(liftToBool(operation)))
}