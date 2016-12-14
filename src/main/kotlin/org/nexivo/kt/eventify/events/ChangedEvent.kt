package org.nexivo.kt.eventify.events

import org.nexivo.kt.eventify.api.Event

interface ChangedEvent<out S, out T, out E: Event> : BasicEvent<S, E> {

    val newValue: T?

    val oldValue: T?
}