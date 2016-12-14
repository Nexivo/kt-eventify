package org.nexivo.kt.eventify

import org.nexivo.kt.eventify.api.Event
import org.nexivo.kt.eventify.api.Message
import org.nexivo.kt.eventify.dsl.onChangedEvent
import org.nexivo.kt.eventify.dsl.onErrorEvent
import org.nexivo.kt.eventify.dsl.onEvent
import org.nexivo.kt.eventify.dsl.onMessageEvent

interface EventProducer<S, E>
    where S: EventProducer<S, E>,
          E: Event {

    val events: EventPublisher<S, E>

    infix fun <T> E.changed(newValue: T?) {

        onChangedEvent(newValue, this)
    }

    infix fun <T> E.changed(values: Pair<T?, T?>) {

        onChangedEvent(values.first, values.second, this)
    }

    infix fun <T> E.`error on`(error: Throwable) {

        onErrorEvent(error, null, this)
    }

    fun E.trigger(): Unit {

        onEvent(this)
    }

    infix fun <T: Message> T.message(message: String): Unit {

        onMessageEvent(this, message)
    }
}

