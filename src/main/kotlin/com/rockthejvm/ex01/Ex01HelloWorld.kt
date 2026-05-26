package com.rockthejvm.ex01

import java.awt.Font
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

private const val INITIAL_TEXT = "Welcome to Mobile Module 00"
private const val HELLO_TEXT = "Hello World!"

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("ex01 - Say Hello to the World")
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        val root = JPanel(GridBagLayout())

        val content = JPanel()
        content.layout = javax.swing.BoxLayout(content, javax.swing.BoxLayout.Y_AXIS)

        val label = JLabel(INITIAL_TEXT, SwingConstants.CENTER).apply {
            alignmentX = 0.5f
            font = font.deriveFont(Font.BOLD, 18f)
        }

        val button = JButton("Toggle text").apply {
            alignmentX = 0.5f
            addActionListener {
                label.text = if (label.text == INITIAL_TEXT) HELLO_TEXT else INITIAL_TEXT
                println("Button pressed")
            }
        }

        content.add(label)
        content.add(javax.swing.Box.createVerticalStrut(16))
        content.add(button)

        root.add(content)
        frame.contentPane = root

        frame.setSize(480, 260)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
}

