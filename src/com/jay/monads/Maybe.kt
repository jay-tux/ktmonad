package com.jay.monads

class Maybe<T>(data: T?) {
    private val data: T? = data

    constructor() : this(null) {}

    fun <T2> fmap(f: (T) -> T2): Maybe<T2> {
        return if(data == null) Maybe() else Maybe(f(data))
    }

    infix fun <T2> `*?*`(f: (T) -> T2): Maybe<T2> {
        return fmap(f)
    }

    fun <T2> ap(f: Maybe<(T) -> T2>): Maybe<T2> {
        return if(data == null || f.data == null) Maybe() else Maybe((f.data)(data))
    }

    infix fun <T2> `***`(f: Maybe<(T) -> T2>): Maybe<T2> {
        return ap(f)
    }

    fun <T2> bind(f: (T) -> Maybe<T2>): Maybe<T2> {
        return if(data == null) Maybe() else f(data)
    }

    infix fun <T2> `**=`(f: (T) -> Maybe<T2>): Maybe<T2> {
        return bind(f)
    }

    companion object {
        fun <T> pure(data:T):Maybe<T> {
            return Maybe(data)
        }
    }
}