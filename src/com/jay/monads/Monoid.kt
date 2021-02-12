package com.jay.monads

interface Monoid<TIn, Return : Monoid<TIn, Return>> {
    fun mempty() : Return
    fun mappend(other: Return): Return
}

class MString(val data: String) : Monoid<String, MString> {
    override fun mempty(): MString = MString("")
    override fun mappend(other: MString): MString = MString(this.data + other.data)
}