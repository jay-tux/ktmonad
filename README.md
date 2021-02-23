# Kotlin Monads
*Haskell's (popular) monads, written in Kotlin*

## Implementations
This repository contains the following monads:  
 - ``Id<T>`` (Identity)  
 - ``MList<T>`` (Monadic list; a linked-list)
 - ``Maybe<T>`` (Maybe, computations that might or might not produce a value)  
 - ``Either<E, A>`` (Either, computations that can fail with error messages)  
 - ``State<S, A>`` (State, computations that can hold an external state)  
 - ``Reader<E, A>`` (Reader, computations that take information from an environment)  
 - ``Function<A, B>`` (Functions of type ``(A) -> B``)  
 - ``Continuation<R, A>`` (Continuations, functions that also contain information on what to do next)

## Practical Use
Practically none, I'll admit it. This was just a fun side-project to test my own knowledge and skill!

## Limitations
Due to certain limitations (constructors and static (or should I say, ``companion object``) methods not being passed on through inheritance/implementation), there are no ``Functor``, ``Applicative`` and ``Monad`` interfaces. 
This also made it impossible (for me, at least) to implement the ``Writer<W, A>`` monad (because the ``Monoid`` type class (in Haskell) enforces a "static" method: ``mempty``).  
If either static (``companion object``) methods or constructors could be enforced by interfaces, it would be very easy to make a "prettier" implementation of all these monads.  
Another "nice" addition would be extension types (as you can see in the ``Monoid.kt`` file): that way we wouldn't need to make wrappers around other types. 