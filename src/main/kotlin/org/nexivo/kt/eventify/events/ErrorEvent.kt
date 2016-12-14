package org.nexivo.kt.eventify.events

import org.nexivo.kt.eventify.api.Event

interface ErrorEvent<out S, out E: Event> : BasicEvent<S, E> {

    val error: Throwable

    val message: String?
}