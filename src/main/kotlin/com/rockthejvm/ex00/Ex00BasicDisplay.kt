package com.rockthejvm.ex00

import java.awt.Font
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("ex00 - A basic display")
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        val root = JPanel(GridBagLayout())

        val content = JPanel()
        content.layout = javax.swing.BoxLayout(content, javax.swing.BoxLayout.Y_AXIS)

        val label = JLabel("Press the button below", SwingConstants.CENTER).apply {
            alignmentX = 0.5f
            font = font.deriveFont(Font.BOLD, 18f)
        }

        val button = JButton("Press me").apply {
            alignmentX = 0.5f
            addActionListener { println("Button pressed") }
        }

        content.add(label)
        content.add(javax.swing.Box.createVerticalStrut(16))
        content.add(button)

        root.add(content)
        frame.contentPane = root

        frame.setSize(420, 260)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
}

