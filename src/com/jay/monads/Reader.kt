package com.jay.monads

class Reader<E, A>(private val run: (E) -> A) {
    fun runReader(env: E): A = run(env)

    fun <B> fmap(f: (A) -> B): Reader<E, B> {
        return Reader { env -> f(run(env)) }
    }

    infix fun <B> `*?*`(f: (A) -> B): Reader<E, B> = fmap(f)

    fun <B> ap(f: Reader<E, (A) -> B>): Reader<E, B> {
        return Reader { env ->
            val func = f.run(env)
            func(run(env))
        }
    }

    infix fun <B> `***`(f: Reader<E, (A) -> B>): Reader<E, B> = ap(f)

    fun <B> bind(f: (A) -> Reader<E, B>): Reader<E, B> {
        return Reader { env -> f(run(env)).run(env) }
    }

    infix fun <B> `**=`(f: (A) -> Reader<E, B>): Reader<E, B> = bind(f)

    companion object {
        fun <E, A> pure(run: (E) -> A): Reader<E, A> = Reader(run)
    }
}