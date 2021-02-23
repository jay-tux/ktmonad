package com.jay.monads

class Continuation<R, A>(private val runCont : ((A) -> R) -> R) {

    fun <B> fmap(f: (A) -> B): Continuation<R, B> = Continuation { br -> runCont { a -> br(f(a)) } }
    fun <B> `*?*`(f: (A) -> B): Continuation<R, B> = fmap(f)
    fun <B> ap(cf: Continuation<R, (A) -> B>): Continuation<R, B> =
        Continuation { b -> cf.runCont { func -> runCont { dat -> b(func(dat)) } } }
    fun <B> `***`(cf: Continuation<R, (A) -> B>): Continuation<R, B> = ap(cf)
    fun <B> bind(mf: (A) -> Continuation<R, B>): Continuation<R, B> =
        Continuation { k -> runCont{ a -> mf(a).runCont(k) } }
    fun <B> `**=`(mf: (A) -> Continuation<R, B>): Continuation<R, B> = bind(mf)

    companion object {
        fun <R, A> pure(f: ((A) -> R) -> R): Continuation<R, A> = Continuation(f)
    }

}