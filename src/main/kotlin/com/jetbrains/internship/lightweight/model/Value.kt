package com.jetbrains.internship.lightweight.model

sealed class Value<out T> {
    abstract val value: T
    abstract val type: Type
}

data class Num(override val value: Int) : Value<Int>() {
    override val type: Type
        get() = Type.NumType
}

data class Bool(override val value: Boolean) : Value<Boolean>() {
    override val type: Type
        get() = Type.BoolType
}

enum class Type {
    NumType,
    BoolType
}


fun <T> liftFromNumber(function: (Int, Int) -> T): (Num, Num) -> T {
    return { a: Num, b: Num -> function(a.value, b.value) }
}
fun <T> liftToNumber(function: (T, T) -> Int): (T, T) -> Num {
    return { a: T, b: T -> Num(function(a, b)) }
}

fun <T> liftFromBool(function: (Boolean, Boolean) -> T): (Bool, Bool) -> T {
    return { a: Bool, b: Bool -> function(a.value, b.value) }
}
fun <T> liftToBool(function: (T, T) -> Boolean): (T, T) -> Bool {
    return { a: T, b: T -> Bool(function(a, b)) }
}
