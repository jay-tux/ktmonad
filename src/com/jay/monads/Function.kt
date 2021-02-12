package com.jay.monads

class Function<A, B>(val runFunc: (A) -> B) {
    fun run(arg: A): B = runFunc(arg)

    fun <C> fmap(f: (B) -> C): Function<A, C> = Function { a -> f(runFunc(a)) }
    fun <C> `*?*`(f: (B) -> C): Function<A, C> = fmap(f)
    fun <C> ap(f: Function<A, (B) -> C>): Function<A, C> = Function { a -> (f.run(a))(run(a)) }
    fun <C> `***`(f: Function<A, (B) -> C>): Function<A, C> = ap(f)
    fun <C> bind(f: (B) -> Function<A, C>): Function<A, C> = Function { a -> (f(run(a))).run(a) }
    fun <C> `**=`(f: (B) -> Function<A, C>): Function<A, C> = bind(f)

    companion object {
        fun <A, B> pure(f: (A) -> B): Function<A, B> = Function(f)
    }
}