package edu.jproyo.core.cat;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Either Sum Type representation on Java
 *
 * @param <L> the type parameter
 * @param <R> the type parameter
 */
public abstract class Either<L,R>
{
    /**
     * Left either.
     *
     * @param <L>   the type parameter
     * @param <R>   the type parameter
     * @param value the value
     * @return the either
     */
    public static <L,R> Either<L,R> left(L value) {
        return new Either<L,R>() {
            @Override public <T> T map(Function<? super L, ? extends T> lFunc,
                                       Function<? super R, ? extends T> rFunc) {
                return lFunc.apply(value);
            }
        };
    }

    /**
     * Right either.
     *
     * @param <L>   the type parameter
     * @param <R>   the type parameter
     * @param value the value
     * @return the either
     */
    public static <L,R> Either<L,R> right(R value) {
        return new Either<L,R>() {
            @Override public <T> T map(Function<? super L, ? extends T> lFunc,
                                       Function<? super R, ? extends T> rFunc) {
                return rFunc.apply(value);
            }

        };
    }
    private Either() {}

    /**
     * Map t.
     *
     * @param <T>   the type parameter
     * @param lFunc the l func
     * @param rFunc the r func
     * @return the t
     */
    public abstract <T> T map(
            Function<? super L, ? extends T> lFunc, Function<? super R, ? extends T> rFunc);

    /**
     * Map left either.
     *
     * @param <T>   the type parameter
     * @param lFunc the l func
     * @return the either
     */
    public <T> Either<T,R> mapLeft(Function<? super L, ? extends T> lFunc) {
        return this.<Either<T,R>>map(t -> left(lFunc.apply(t)), t -> (Either<T,R>)this);
    }

    /**
     * Map right either.
     *
     * @param <T>   the type parameter
     * @param lFunc the l func
     * @return the either
     */
    public <T> Either<L,T> mapRight(Function<? super R, ? extends T> lFunc) {
        return this.<Either<L,T>>map(t -> (Either<L,T>)this, t -> right(lFunc.apply(t)));
    }

    /**
     * Apply.
     *
     * @param lFunc the l func
     * @param rFunc the r func
     */
    public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc) {
        map(consume(lFunc), consume(rFunc));
    }
    private <T> Function<T,Void> consume(Consumer<T> c) {
        return t -> { c.accept(t); return null; };
    }
}
