package com.jetbrains.internship.lightweight.parse.model

class Element : Expression {
    override fun equals(other: Any?): Boolean = other is Element
    override fun hashCode(): Int = javaClass.hashCode()
}