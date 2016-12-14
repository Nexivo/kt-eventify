package org.nexivo.kt.eventify

import org.nexivo.kt.eventify.api.Event
import org.nexivo.kt.eventify.events.BasicEvent
import rx.Observable
import rx.subjects.PublishSubject

class EventPublisher<S : EventProducer<S, E>, E : Event>

    private constructor(internal val subject: PublishSubject<BasicEvent<S, E>>)

    : Observable<BasicEvent<S, E>>({ subject.subscribe(it) }) {

    companion object {

        fun <S : EventProducer<S, E>, E : Event> create(): EventPublisher<S, E>
            = EventPublisher(PublishSubject.create())
    }
}