package com.jay.monads

class MList<T> private constructor(private val data: T?, private val next: MList<T>?, discriminator: Boolean) {

    constructor(): this(null, null, false) { }
    constructor(data: T): this(data, MList()) { }
    constructor(data: T, next: MList<T>): this(data, next, false) { }

    infix fun `+++`(other: MList<T>): MList<T> {
        return if(data == null) other else (next!!) `+++` other
    }

    fun <T2> fmap(f: (T) -> T2): MList<T2> {
        return if(data == null) MList() else (MList(f(data)) `+++` next!!.fmap(f))
    }

    infix fun <T2> `*?*`(f: (T) -> T2): MList<T2> = fmap(f)

    fun <T2> ap(f: MList<(T) -> T2>): MList<T2> {
        return if(data == null || f.data == null) MList() else (MList((f.data)(data)) `+++` next!!.ap(f))
    }

    infix fun <T2> `***`(f: MList<(T) -> T2>): MList<T2> = ap(f)

    fun <T2> bind(f: (T) -> MList<T2>): MList<T2> {
        return if(data == null) MList() else f(data) `+++` next!!.bind(f)
    }

    infix fun <T2> `**=`(f: (T) -> MList<T2>): MList<T2> = bind(f)

    companion object {
        fun <T> pure(data:T):MList<T> = MList(data)
    }
}