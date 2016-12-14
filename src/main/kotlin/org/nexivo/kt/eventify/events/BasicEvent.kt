package org.nexivo.kt.eventify.events

import org.nexivo.kt.eventify.api.Event

interface BasicEvent<out S, out E: Event> {

    val source: S?

    val event:  E?
}
