package com.jetbrains.internship.lightweight.model

data class CallChain(val head: Call, val tail: CallChain?) : Iterable<Call> {
    override fun iterator(): Iterator<Call> = CallChainIterator(this)
}

private class CallChainIterator(callChain: CallChain) : Iterator<Call> {
    private var currentCallChain: CallChain? = callChain

    override fun hasNext(): Boolean = currentCallChain != null

    override fun next(): Call {
        val currentCall = currentCallChain!!.head
        currentCallChain = currentCallChain!!.tail
        return currentCall
    }

}

fun callChainOf(head: Call, vararg tail: Call) = CallChain(head, tail.foldRight(null, ::CallChain))