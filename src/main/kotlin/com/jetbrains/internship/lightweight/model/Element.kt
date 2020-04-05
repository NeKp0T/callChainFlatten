package com.jetbrains.internship.lightweight.model

class Element : Expression {
    override fun <T> accept(visitor: ExpressionVisitor<T>): T = visitor.visitElement(this)

    override fun equals(other: Any?): Boolean = other is Element
    override fun hashCode(): Int = javaClass.hashCode()
}