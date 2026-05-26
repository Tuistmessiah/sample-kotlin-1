package com.rockthejvm.ex02

import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("ex02 - More Buttons")
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.layout = BorderLayout()

        val appBar = JLabel("Calculator").apply {
            border = BorderFactory.createEmptyBorder(12, 16, 12, 16)
            font = font.deriveFont(Font.BOLD, 18f)
        }
        frame.add(appBar, BorderLayout.NORTH)

        val displayPanel = JPanel(GridLayout(2, 1, 0, 8)).apply {
            border = BorderFactory.createEmptyBorder(16, 16, 12, 16)
        }

        val expressionField = JTextField("0").apply {
            isEditable = false
        }
        val resultField = JTextField("0").apply {
            isEditable = false
        }

        displayPanel.add(expressionField)
        displayPanel.add(resultField)

        val buttonsPanel = JPanel(GridLayout(5, 4, 8, 8)).apply {
            border = BorderFactory.createEmptyBorder(0, 16, 16, 16)
        }

        val labels = listOf(
            "AC", "C", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", "",
        )

        labels.forEach { label ->
            if (label.isBlank()) {
                buttonsPanel.add(JPanel())
            } else {
                val button = JButton(label).apply {
                    font = font.deriveFont(Font.BOLD, 16f)
                    addActionListener { println(label) }
                }
                buttonsPanel.add(button)
            }
        }

        val center = JPanel(BorderLayout()).apply {
            add(displayPanel, BorderLayout.NORTH)
            add(buttonsPanel, BorderLayout.CENTER)
        }

        frame.add(center, BorderLayout.CENTER)
        frame.setSize(420, 560)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
}

