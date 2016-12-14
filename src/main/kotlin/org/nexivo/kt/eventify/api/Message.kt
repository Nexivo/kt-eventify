package org.nexivo.kt.eventify.api

interface Message : Event {

    val priority: MessagePriority
}