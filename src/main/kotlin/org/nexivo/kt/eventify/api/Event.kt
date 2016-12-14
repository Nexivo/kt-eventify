package org.nexivo.kt.eventify.api

import java.util.*

interface Event {

    val Type: EnumSet<EventType>
}