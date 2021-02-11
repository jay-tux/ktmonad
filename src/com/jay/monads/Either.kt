package com.jay.monads

class Either<E, A> private constructor(private val data: A?, private val error: E?) {

    constructor(data: A) : this(data, null) { }
    constructor(error: E, overload: Boolean) : this(null, error) { }

    fun <B> fmap(f: (A) -> B): Either<E, B> {
        return if(this.data == null) Either(error!!, false) else Either(f(data))
    }

    infix fun <B> `*?*`(f: (A) -> B): Either<E, B> = fmap(f)

    fun <B> ap(f: Either<E, (A) -> B>): Either<E, B> {
        return if(this.data == null || f.data == null) Either(error!!, false) else Either((f.data)(data))
    }

    fun <B> `***`(f: Either<E, (A) -> B>): Either<E, B> = ap(f)

    fun <B> bind(f: (A) -> Either<E, B>): Either<E, B> {
        return if(this.data == null) Either(error!!, false) else f(this.data)
    }

    infix fun <B> `**=`(f: (A) -> Either<E, B>): Either<E, B> = bind(f)

    companion object {
        fun <E, A> pure(data: A): Either<E, A> = Either(data)
    }
}