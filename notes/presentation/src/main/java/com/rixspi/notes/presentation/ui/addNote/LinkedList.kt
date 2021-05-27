package com.rixspi.notes.presentation.ui.addNote

class LinkedList {
    private var head: String? = null
    private val nodes: MutableMap<String, String?> = mutableMapOf()


    fun add(id: String, previousId: String? = null) {
        if (head == null) {
            head = id
        }

        nodes[id] = previousId
    }

    fun getAll(): List<String> {
        val head = head
        var next: String? = head
        val all = mutableListOf<String>()
        if (head != null) {
            all.add(head)
        }
        while (next != null) {
            all.add(next)
            next = nodes[next]
        }
        return all
    }
}