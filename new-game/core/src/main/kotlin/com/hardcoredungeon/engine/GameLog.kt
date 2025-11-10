package com.hardcoredungeon.engine

import com.badlogic.gdx.graphics.Color

/**
 * Game message log for player feedback
 */
object GameLog {

    data class Message(val text: String, val color: Color = Color.WHITE, val timestamp: Long = System.currentTimeMillis())

    private val messages = mutableListOf<Message>()
    private const val MAX_MESSAGES = 100

    fun add(text: String, color: Color = Color.WHITE) {
        messages.add(0, Message(text, color))

        // Keep only recent messages
        while (messages.size > MAX_MESSAGES) {
            messages.removeLast()
        }
    }

    fun info(text: String) {
        add(text, Color.WHITE)
    }

    fun warning(text: String) {
        add(text, Color.YELLOW)
    }

    fun error(text: String) {
        add(text, Color.RED)
    }

    fun positive(text: String) {
        add(text, Color.GREEN)
    }

    fun combat(text: String) {
        add(text, Color.ORANGE)
    }

    fun getRecent(count: Int = 5): List<Message> {
        return messages.take(count)
    }

    fun getAll(): List<Message> {
        return messages.toList()
    }

    fun clear() {
        messages.clear()
    }
}
