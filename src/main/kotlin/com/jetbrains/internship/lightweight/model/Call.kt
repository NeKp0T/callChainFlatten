package com.jetbrains.internship.lightweight.model

sealed class Call

data class FilterCall(val predicate: Expression) : Call()

data class MapCall(val transform: Expression) : Call()

data class CallChain(val head: Call, val tail: CallChain?) : Iterable<Call> {
    override fun iterator(): Iterator<Call> = object : Iterator<Call> {
        override fun hasNext(): Boolean = tail != null

        override fun next(): Call = tail!!.head
    }
}

fun callChainOf(head: Call, vararg tail: Call) = CallChain(head, tail.foldRight(null, ::CallChain))