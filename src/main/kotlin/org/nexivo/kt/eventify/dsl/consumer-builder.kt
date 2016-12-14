package org.nexivo.kt.eventify.dsl

import org.nexivo.kt.eventify.EventConsumer
import org.nexivo.kt.eventify.EventProducer
import org.nexivo.kt.eventify.api.Event

fun <S : EventProducer<S, E>, E: Event> consume(producer: EventProducer<S, E>, init: EventConsumer<S, E>.() -> Unit): EventConsumer<S, E> {

    val result: EventConsumer<S, E> = EventConsumer(producer)

    result.init()

    return result
}