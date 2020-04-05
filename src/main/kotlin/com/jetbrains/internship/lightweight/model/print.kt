package com.jetbrains.internship.lightweight.model

fun printExpression(expression: Expression): String {
    val visitor = PrinterVisitor(StringBuilder())
    expression.accept(visitor)
    return visitor.builder.toString()
}

private class PrinterVisitor(val builder: StringBuilder) : ExpressionVisitor<Unit> {
    override fun visitElement(expression: Element) {
        builder.append("element")
    }

    override fun visitConstantExpression(expression: ConstantExpression<*>) {
        builder.append(expression.value.value)
    }

    override fun visitBinaryExpression(expression: BinaryExpression) {
        builder.append("(")
        expression.left.accept(this)
        builder.append(expression.operationSign)
        expression.right.accept(this)
        builder.append(")")
    }
}