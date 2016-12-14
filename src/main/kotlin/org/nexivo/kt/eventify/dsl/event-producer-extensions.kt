package org.nexivo.kt.eventify.dsl

import org.nexivo.kt.eventify.EventProducer
import org.nexivo.kt.eventify.api.Event
import org.nexivo.kt.eventify.api.EventType
import org.nexivo.kt.eventify.api.Message

fun <S: EventProducer<S, E>, T, E: Event> EventProducer<S, E>.onChangedEvent(old: T?, new: T?, event: E): T? {

    if (!event.Type.contains(EventType.Change)) {
        throw IllegalArgumentException("$event is not a Change Event!")
    }

    if (old != new) {
        @Suppress("UNCHECKED_CAST")
        this.events.subject.onNext(changedEvent(this as S, old, new, event))
    }

    return new
}

fun <S: EventProducer<S, E>, T, E: Event> EventProducer<S, E>.onChangedEvent(new: T?, event: E): Unit {

    if (event.Type.contains(EventType.Change)) {
        @Suppress("UNCHECKED_CAST")
        this.events.subject.onNext(changedEvent(this as S, null, new, event))
    } else {
        throw IllegalArgumentException("$event is not a Change Event!")
    }
}

fun <S : EventProducer<S, E>, E : Event> EventProducer<S, E>.onEvent(event: E): Unit {

    if (event.Type.contains(EventType.Basic)){
        @Suppress("UNCHECKED_CAST")
        this.events.subject.onNext(event(this as S, event))
    } else {
        throw IllegalArgumentException("$event is not an Event!")
    }
}

fun <S : EventProducer<S, E>, E : Event> EventProducer<S, E>.onErrorEvent(error: Throwable, message: String? = null, event: E): Unit {

    if (event.Type.contains(EventType.Error)) {
        @Suppress("UNCHECKED_CAST")
        this.events.subject.onNext(errorEvent(this as S, error, message, event))
    } else {
        throw IllegalArgumentException("$event is not an Error Event!")
    }
}

fun <S : EventProducer<S, E>, E : Event> EventProducer<S, E>.onError(exception: Throwable): Unit {

    this.events.subject.onError(exception)
}

fun <S: EventProducer<S, E>, T: Message, E: Event> EventProducer<S, E>.onMessageEvent(messageType: T, message: String?): Unit {

    @Suppress("UNCHECKED_CAST")
    this.events.subject.onNext(messageEvent(this as S, messageType, message))
}
