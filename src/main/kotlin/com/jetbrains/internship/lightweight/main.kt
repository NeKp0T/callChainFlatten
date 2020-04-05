package com.jetbrains.internship.lightweight

import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.Parsed
import com.jetbrains.internship.lightweight.flatten.flattenCallChain
import com.jetbrains.internship.lightweight.model.CallChain
import com.jetbrains.internship.lightweight.parse.ExprGrammar

fun main() {
    val callChainInput = readLine() ?: return
    when (val parseResult = ExprGrammar.tryParseToEnd(callChainInput)) {
        is Parsed<CallChain> -> {
            val chain = parseResult.value
            val flatChain = flattenCallChain(chain)
            if (flatChain == null) {
                println("TYPE ERROR")
            } else {
                println()
            }
        }
        is ErrorResult -> {
            println("SYNTAX ERROR")
        }
    }

}