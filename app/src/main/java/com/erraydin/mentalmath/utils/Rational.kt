package com.erraydin.mentalmath.utils

import java.lang.IllegalArgumentException
import kotlin.math.abs

class Rational(num: Int, denom: Int) {
    private val numerator: Int
    private val denominator: Int

    init {
        if (denom == 0) throw IllegalArgumentException("Denominator cannot be 0!")

        val gcdNumDenom = gcd(num, denom)

        if ( num * denom < 0) {
            numerator = -abs(num)/ gcdNumDenom
            denominator = abs(denom) / gcdNumDenom
        } else {
            numerator = abs(num)/ gcdNumDenom
            denominator = abs(denom) / gcdNumDenom
        }

    }

    operator fun unaryMinus() = Rational(
        -numerator,
        denominator
    )

    operator fun times(other: Rational) = Rational(
        numerator * other.numerator,
        denominator * other.denominator
    )

    operator fun plus(other: Rational) = Rational(
        numerator * other.denominator + denominator * other.numerator,
        denominator * other.denominator
    )

    operator fun minus(other: Rational) = Rational(
        numerator * other.denominator - denominator * other.numerator,
        denominator * other.denominator
    )

    operator fun div(other: Rational) = Rational(
        numerator * other.denominator,
        denominator * other.numerator
    )

    private fun gcd(a: Int, b: Int): Int {
        var n1 = abs(a)
        var n2 = abs(b)

        while (n1 != n2) {
            if (n1 > n2) {
                n1 -= n2
            } else {
                n2 -= n1
            }
        }
        return n1
    }

    override fun toString(): String {
        return "$numerator/$denominator"
    }
}