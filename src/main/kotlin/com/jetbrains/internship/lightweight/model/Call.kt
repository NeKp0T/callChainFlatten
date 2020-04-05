package com.jetbrains.internship.lightweight.model

sealed class Call

data class FilterCall(val predicate: Expression) : Call()

data class MapCall(val transform: Expression) : Call()