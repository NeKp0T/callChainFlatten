package com.jetbrains.internship.lightweight

import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.Parsed
import com.jetbrains.internship.lightweight.flatten.flattenCallChain
import com.jetbrains.internship.lightweight.model.CallChain
import com.jetbrains.internship.lightweight.model.printCallChain
import com.jetbrains.internship.lightweight.parse.ExprGrammar

fun main(args: Array<String>) {
    val callChainInput = readLine() ?: return
    when (val parseResult = ExprGrammar.tryParseToEnd(callChainInput)) {
        is Parsed<CallChain> -> {
            val chain = parseResult.value
            val flatChain = flattenCallChain(chain)
            if (flatChain == null) {
                println("TYPE ERROR")
            } else {
                println(printCallChain(flatChain))
            }
        }
        is ErrorResult -> {
            println("SYNTAX ERROR")
        }
    }

}