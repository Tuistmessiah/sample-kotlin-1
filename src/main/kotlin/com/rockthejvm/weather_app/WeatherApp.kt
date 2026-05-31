package com.rockthejvm.weather_app

import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

private enum class WeatherTab(val label: String, val icon: String) {
    CURRENTLY("Currently", "\u2600"),
    TODAY("Today", "\uD83D\uDCC5"),
    WEEKLY("Weekly", "\uD83D\uDCCA"),
}

private sealed class LocationMode {
    data object None : LocationMode()
    data class Search(val query: String) : LocationMode()
    data object Geolocation : LocationMode()
}

fun main() {
    SwingUtilities.invokeLater { WeatherAppFrame().isVisible = true }
}

private class WeatherAppFrame : JFrame("weather_app - mobileModule01") {
    private var selectedTabIndex = 0
    private var locationMode: LocationMode = LocationMode.None
    private var dragStartX = 0

    private val tabContentLabels = WeatherTab.entries.map { tab ->
        JLabel(defaultContentText(tab), SwingConstants.CENTER).apply {
            font = font.deriveFont(Font.PLAIN, 22f)
        }
    }

    private val contentPanel = JPanel(CardLayout()).apply {
        border = BorderFactory.createEmptyBorder(24, 24, 24, 24)
        WeatherTab.entries.forEachIndexed { index, _ ->
            add(tabContentLabels[index], index.toString())
        }
    }

    private val searchField = JTextField().apply {
        toolTipText = "Search location"
    }

    private val tabButtons = WeatherTab.entries.mapIndexed { index, tab ->
        createTabButton(tab, index)
    }

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        layout = BorderLayout()
        minimumSize = Dimension(320, 480)
        setSize(420, 640)
        setLocationRelativeTo(null)

        add(createTopBar(), BorderLayout.NORTH)
        add(createSwipeableContent(), BorderLayout.CENTER)
        add(createBottomBar(), BorderLayout.SOUTH)

        selectTab(0)
    }

    private fun createTopBar(): JPanel {
        val topBar = JPanel(BorderLayout(8, 0)).apply {
            border = BorderFactory.createEmptyBorder(12, 16, 12, 16)
        }

        searchField.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) = onSearchChanged()
            override fun removeUpdate(e: DocumentEvent?) = onSearchChanged()
            override fun changedUpdate(e: DocumentEvent?) = onSearchChanged()
        })

        val geolocationButton = JButton("\u2316").apply {
            toolTipText = "Geolocation"
            font = font.deriveFont(Font.BOLD, 18f)
            addActionListener {
                locationMode = LocationMode.Geolocation
                refreshAllTabContent()
            }
        }

        topBar.add(searchField, BorderLayout.CENTER)
        topBar.add(geolocationButton, BorderLayout.EAST)
        return topBar
    }

    private fun createSwipeableContent(): JPanel {
        val wrapper = JPanel(BorderLayout()).apply {
            add(contentPanel, BorderLayout.CENTER)
        }

        val mouseHandler = object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                dragStartX = e.xOnScreen
            }

            override fun mouseReleased(e: MouseEvent) {
                val delta = e.xOnScreen - dragStartX
                when {
                    delta > 80 -> selectTab(selectedTabIndex - 1)
                    delta < -80 -> selectTab(selectedTabIndex + 1)
                }
            }
        }

        wrapper.addMouseListener(mouseHandler)
        contentPanel.addMouseListener(mouseHandler)
        tabContentLabels.forEach { it.addMouseListener(mouseHandler) }

        return wrapper
    }

    private fun createBottomBar(): JPanel {
        val bottomBar = JPanel(GridLayout(1, WeatherTab.entries.size)).apply {
            border = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY)
        }

        tabButtons.forEach { bottomBar.add(it) }
        return bottomBar
    }

    private fun createTabButton(tab: WeatherTab, index: Int): JPanel {
        val iconLabel = JLabel(tab.icon, SwingConstants.CENTER).apply {
            font = font.deriveFont(Font.PLAIN, 20f)
            alignmentX = 0.5f
        }
        val nameLabel = JLabel(tab.label, SwingConstants.CENTER).apply {
            font = font.deriveFont(Font.PLAIN, 12f)
            alignmentX = 0.5f
        }

        return JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            add(Box.createVerticalGlue())
            add(iconLabel)
            add(Box.createVerticalStrut(4))
            add(nameLabel)
            add(Box.createVerticalGlue())
            border = BorderFactory.createEmptyBorder(8, 8, 8, 8)
            cursor = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR)
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    selectTab(index)
                }
            })
        }
    }

    private fun onSearchChanged() {
        val query = searchField.text.trim()
        locationMode = if (query.isEmpty()) LocationMode.None else LocationMode.Search(query)
        refreshAllTabContent()
    }

    private fun selectTab(index: Int) {
        val boundedIndex = index.coerceIn(0, WeatherTab.entries.lastIndex)
        selectedTabIndex = boundedIndex

        val cardLayout = contentPanel.layout as CardLayout
        cardLayout.show(contentPanel, boundedIndex.toString())

        tabButtons.forEachIndexed { tabIndex, panel ->
            val selected = tabIndex == boundedIndex
            panel.background = if (selected) Color(230, 240, 255) else Color.WHITE
            panel.isOpaque = true
        }
    }

    private fun refreshAllTabContent() {
        WeatherTab.entries.forEachIndexed { index, tab ->
            tabContentLabels[index].text = contentTextFor(tab)
        }
    }

    private fun contentTextFor(tab: WeatherTab): String {
        val suffix = when (val mode = locationMode) {
            LocationMode.None -> return tab.label
            LocationMode.Geolocation -> "Geolocation"
            is LocationMode.Search -> mode.query
        }
        return "${tab.label} - $suffix"
    }

    private fun defaultContentText(tab: WeatherTab): String = tab.label
}
