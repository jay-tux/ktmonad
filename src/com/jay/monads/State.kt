package com.jay.monads

class State<S, A> private constructor(private val run: (S) -> Pair<A, S>) {

    fun runState(initstate: S): Pair<A, S> = run(initstate)

    fun <B> fmap(f: (A) -> B): State<S, B> {
        return State { s ->
            val new = run(s)
            Pair(f(new.first), new.second)
        }
    }

    infix fun <B> `*?*`(f: (A) -> B): State<S, B> = fmap(f)

    fun <B> ap(f: State<S, (A) -> B>): State<S, B> {
        return State { s ->
            val new = run(s)
            val func = f.run(new.second)
            Pair(func.first(new.first), func.second)
        }
    }

    infix fun <B> `***`(f: State<S, (A) -> B>): State<S, B> = ap(f)

    fun <B> bind(f: (A) -> State<S, B>): State<S, B> {
        return State { s ->
            val new = run(s)
            f(new.first).run(new.second)
        }
    }

    infix fun <B> `**=`(f: (A) -> State<S, B>): State<S, B> = bind(f)

    companion object {
        fun <S, A> pure(run: (S) -> Pair<A, S>): State<S, A> = State(run)
    }
}