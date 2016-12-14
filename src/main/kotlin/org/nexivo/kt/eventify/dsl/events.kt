package org.nexivo.kt.eventify.dsl

import org.nexivo.kt.eventify.EventProducer
import org.nexivo.kt.eventify.api.Event
import org.nexivo.kt.eventify.api.Message
import org.nexivo.kt.eventify.events.ChangedEvent
import org.nexivo.kt.eventify.events.ErrorEvent
import org.nexivo.kt.eventify.events.BasicEvent
import org.nexivo.kt.eventify.events.MessageEvent

fun <S: EventProducer<S, E>, E: Event> event(source: S?, event: E): BasicEvent<S, E>
    = object : BasicEvent<S, E> {

        override val event:  E  = event
        override val source: S? = source
    }

fun <S: EventProducer<S, E>, E: Event> errorEvent(source: S?, error: Throwable, message: String? = null, event: E? = null): BasicEvent<S, E>
    = object : ErrorEvent<S, E> {

        override val event:   E?        = event
        override val error:   Throwable = error
        override val message: String?   = message
        override val source:  S?        = source
    }

fun <S: EventProducer<S, E>, T, E: Event> changedEvent(source: S?, old: T?, new: T?, event: E): ChangedEvent<S, T, E>
    = object : ChangedEvent<S, T, E> {

        override val event:    E  = event
        override val newValue: T? = new
        override val oldValue: T? = old
        override val source:   S? = source
    }

fun <S: EventProducer<S, E>, T: Message, E: Event> messageEvent(source: S?, messageType: T, message: String?): org.nexivo.kt.eventify.events.MessageEvent<S, T, E>
    = object : MessageEvent<S, T, E> {

        override val event:   E?      = null
        override val message: String? = message
        override val type:    T       = messageType
        override val source:  S?      = source
    }
