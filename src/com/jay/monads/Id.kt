package com.jay.monads

class Id<T>(private val data: T) {

    fun <T2> fmap(f: (T) -> T2): Id<T2> { return Id(f(data)) }
    infix fun <T2> `*?*`(f: (T) -> T2): Id<T2> = fmap(f)
    fun <T2> ap(f: Id<(T) -> T2>): Id<T2> { return Id(f.data(data)) }
    infix fun <T2> `***`(f: Id<(T) -> T2>): Id<T2> = ap(f)
    fun <T2> bind(f: (T) -> Id<T2>): Id<T2> { return f(data) }
    infix fun <T2> `**=`(f: (T) -> Id<T2>): Id<T2> = bind(f)

    companion object {
        fun <T> pure(data: T): Id<T> = Id(data)
    }
}