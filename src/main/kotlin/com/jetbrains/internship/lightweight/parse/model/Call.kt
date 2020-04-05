package com.jetbrains.internship.lightweight.parse.model

sealed class Call

class FilterCall(val predicate: Expression) : Call()

class MapCall(val transform: Expression) : Call()