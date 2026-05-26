package com.rockthejvm.calculator

import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorLogic(
    initialExpression: String = "0",
    initialResult: String = "0",
) {
    var expression: String = initialExpression
        private set

    var result: String = initialResult
        private set

    fun onButtonPress(label: String) {
        when (label) {
            "AC" -> clearAll()
            "C" -> deleteLastCharacter()
            "=" -> evaluate()
            else -> appendToken(label)
        }
    }

    private fun clearAll() {
        expression = "0"
        result = "0"
    }

    private fun deleteLastCharacter() {
        expression = when {
            expression.length <= 1 -> "0"
            else -> expression.dropLast(1)
        }
    }

    private fun appendToken(label: String) {
        val digitsOrDot = setOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".")
        val operators = setOf("+", "-", "*", "/")

        expression = when {
            expression == "0" && label in digitsOrDot -> label
            expression == "0" && label == "-" -> "-"
            expression == "0" && label in setOf("+", "*", "/") -> "0$label"
            expression == "0" && label == "." -> "0."

            endsWithOperator() && label in operators ->
                expression.dropLast(1) + label

            endsWithOperator() && label == "." ->
                expression + "0."

            endsWithOperator() && label == "-" ->
                expression + label

            else -> expression + label
        }
    }

    private fun endsWithOperator(): Boolean {
        val last = expression.lastOrNull() ?: return false
        return last in setOf('+', '-', '*', '/')
    }

    private fun evaluate() {
        result = try {
            val normalized = normalizeExpression(expression)
            val value = ExpressionBuilder(normalized).build().evaluate()
            if (value.isNaN() || value.isInfinite()) "Error" else formatNumber(value)
        } catch (_: ArithmeticException) {
            "Error"
        } catch (_: Exception) {
            "Error"
        }
    }

    private fun normalizeExpression(raw: String): String {
        var normalized = raw
        while (normalized.isNotEmpty() && normalized.last() in setOf('+', '-', '*', '/')) {
            normalized = normalized.dropLast(1)
        }
        return if (normalized.isEmpty()) "0" else normalized
    }

    private fun formatNumber(value: Double): String {
        val plain = if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            String.format("%.10f", value).trimEnd('0').trimEnd('.')
        }

        return if (plain.length > 20) String.format("%.6e", value) else plain
    }
}

