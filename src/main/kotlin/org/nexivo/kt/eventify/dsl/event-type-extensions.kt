package org.nexivo.kt.eventify.dsl

import org.nexivo.kt.eventify.api.EventType
import java.util.*

fun Array<out EventType>.toEnumSet(): EnumSet<EventType>
    = if (this.isEmpty()) {
        EnumSet.noneOf(EventType::class.java)
    } else {
        EnumSet.of(this[0], *this.filterIndexed { idx, _ ->  idx > 0}.toTypedArray())
    }