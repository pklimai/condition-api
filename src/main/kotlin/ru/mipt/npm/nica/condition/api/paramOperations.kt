package ru.mipt.npm.nica.condition.api

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.between
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq

/*
 Following functions create SQL operation from string parameter value.
 Can include ranges (x-y), greater-or-equal (x+), less-or-equal(x-)
 Note: 'in' fixes optional columns
*/
fun intParameterOperation(strParamValue: String?, col: Column<in Int>): Op<Boolean>? {
    if (strParamValue.isNullOrEmpty()) return null
    if (strParamValue.last() == '-') {
        val x = strParamValue.slice(0..strParamValue.length - 2).toInt()
        return col.lessEq(x)
    }
    if (strParamValue.last() == '+') {
        val x = strParamValue.slice(0..strParamValue.length - 2).toInt()
        return col.greaterEq(x)
    }
    if (strParamValue.contains("-")) {  // but not last
        val x1 = strParamValue.substringBefore("-").toInt()
        val x2 = strParamValue.substringAfter("-").toInt()
        return col.between(x1, x2)
    }
    val xOrNull = strParamValue.toIntOrNull()
    if (xOrNull != null) {
        return col.eq(xOrNull)
    }
    return null
}

fun doubleParameterOperation(strParamValue: String?, col: Column<in Double>): Op<Boolean>? {
    if (strParamValue.isNullOrEmpty()) return null
    if (strParamValue.last() == '-') {
        val x = strParamValue.slice(0..strParamValue.length - 2).toDouble()
        return col.lessEq(x)
    }
    if (strParamValue.last() == '+') {
        val x = strParamValue.slice(0..strParamValue.length - 2).toDouble()
        return col.greaterEq(x)
    }
    if (strParamValue.contains("-")) {  // but not last
        val x1 = strParamValue.substringBefore("-").toDouble()
        val x2 = strParamValue.substringAfter("-").toDouble()
        return col.between(x1, x2)
    }
    val xOrNull = strParamValue.toDoubleOrNull()
    if (xOrNull != null) {
        return col.eq(xOrNull)
    }
    return null
}

fun longParameterOperation(strParamValue: String?, col: Column<in Long>): Op<Boolean>? {
    if (strParamValue.isNullOrEmpty()) return null
    if (strParamValue.last() == '-') {
        val x = strParamValue.slice(0..strParamValue.length - 2).toLong()
        return col.lessEq(x)
    }
    if (strParamValue.last() == '+') {
        val x = strParamValue.slice(0..strParamValue.length - 2).toLong()
        return col.greaterEq(x)
    }
    if (strParamValue.contains("-")) {  // but not last
        val x1 = strParamValue.substringBefore("-").toLong()
        val x2 = strParamValue.substringAfter("-").toLong()
        return col.between(x1, x2)
    }
    val xOrNull = strParamValue.toLongOrNull()
    if (xOrNull != null) {
        return col.eq(xOrNull)
    }
    return null
}

fun stringParameterOperation(strParamValue: String?, col: Column<in String>): Op<Boolean>? {
    if (strParamValue.isNullOrEmpty()) return null
    return col.eq(strParamValue)
}
