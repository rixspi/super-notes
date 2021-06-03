package com.rixspi.notes.presentation.ui.addNote

class LinkedList {
    var head: String? = null
        private set
    var tail: String? = null
        private set

    private data class NodeInfo(var previousId: String?, var nextId: String?)

    private val nodes: MutableMap<String, NodeInfo> = mutableMapOf()

    fun add(id: String, previousId: String? = null) {
        if (previousId == null) {
            head = id
        }

        val previousNodeInfo = nodes[previousId]
        val previousNextNode = nodes[previousNodeInfo?.nextId]

        nodes[id] = NodeInfo(previousId, previousNodeInfo?.nextId)

        previousNodeInfo?.nextId = id
        previousNextNode?.previousId = id

        if (previousNextNode?.nextId == null) {
            tail = id
        }
    }

    fun remove(id: String) {
        val nodeInfo = nodes[id]
        val previousNodeInfo = nodes[nodeInfo?.previousId]
        val nextNodeInfo = nodes[nodeInfo?.nextId]
        if (id == head) {
            head = nodeInfo?.nextId
        }
        if (id == tail) {
            tail = nodeInfo?.previousId
        }
        previousNodeInfo?.nextId = nodeInfo?.nextId
        nextNodeInfo?.previousId = nodeInfo?.previousId
        nodes.remove(id)
    }

    fun getAll(): List<String> {
        var next: String? = head
        val all = mutableListOf<String>()
        while (next != null) {
            all.add(next)
            next = nodes[next]?.nextId
        }
        return all
    }
}