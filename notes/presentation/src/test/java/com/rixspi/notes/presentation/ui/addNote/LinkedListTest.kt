package com.rixspi.notes.presentation.ui.addNote

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LinkedListTest {

    private val linkedList: LinkedList = LinkedList()

    @Test
    fun `add without previous sets head`() {
        linkedList.add("first")

        assertTrue(linkedList.head == "first")
    }

    @Test
    fun `add with previous do not break order`() {
        linkedList.add("first")
        linkedList.add("second", "first")
        linkedList.add("third", "second")

        assertEquals(listOf("first", "second", "third"), linkedList.getAll())

        linkedList.add("newSecond", "first")

        assertEquals(listOf("first", "newSecond", "second", "third"), linkedList.getAll())
    }

    @Test
    fun `add sets tail`() {
        linkedList.add("first")
        linkedList.add("second", "first")
        linkedList.add("third", "second")

        assertEquals("third", linkedList.tail)

        linkedList.add("newSecond", "first")

        assertEquals("third", linkedList.tail)

        linkedList.add("newThird", "third")

        assertEquals("newThird", linkedList.tail)
    }

    @Test
    fun `add at tail`() {
        linkedList.add("first")
        linkedList.add("second", "first")
        linkedList.add("third", "second")

        linkedList.add("newThird", linkedList.tail)
        assertEquals("newThird", linkedList.tail)
        assertEquals(listOf("first", "second", "third", "newThird"), linkedList.getAll())
    }

    @Test
    fun `remove from head`() {
        linkedList.add("first")
        linkedList.add("second", "first")
        linkedList.add("third", "second")

        val head = requireNotNull(linkedList.head)

        linkedList.remove(head)

        assertEquals("second", linkedList.head)
        assertEquals(listOf("second", "third"), linkedList.getAll())
    }

    @Test
    fun `remove from middle`() {
        linkedList.add("first")
        linkedList.add("second", "first")
        linkedList.add("third", "second")

        linkedList.remove("second")

        assertEquals("first", linkedList.head)
        assertEquals("third", linkedList.tail)
        assertEquals(listOf("first", "third"), linkedList.getAll())
    }

    @Test
    fun `remove from tail`() {
        linkedList.add("first")
        linkedList.add("second", "first")
        linkedList.add("third", "second")

        println(linkedList.tail)
        linkedList.remove(requireNotNull(linkedList.tail))

        assertEquals("first", linkedList.head)
        assertEquals("second", linkedList.tail)
        assertEquals(listOf("first", "second"), linkedList.getAll())
    }
}
