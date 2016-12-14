package org.nexivo.kt.eventify.events

import org.nexivo.kt.eventify.api.Event
import org.nexivo.kt.eventify.api.Message

interface MessageEvent<out S, out T: Message, out E: Event> : BasicEvent<S, E> {

    val message: String?

    val type:    T
}