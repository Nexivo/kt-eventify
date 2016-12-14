package org.nexivo.kt.eventify

import org.nexivo.kt.eventify.api.Event
import org.nexivo.kt.eventify.api.Message
import org.nexivo.kt.eventify.events.BasicEvent
import org.nexivo.kt.eventify.events.ChangedEvent
import org.nexivo.kt.eventify.events.ErrorEvent
import org.nexivo.kt.eventify.events.MessageEvent
import rx.Subscription
import rx.lang.kotlin.onError
import java.io.Closeable

class EventConsumer<S : EventProducer<S, E>, E: Event>(producer: EventProducer<S, E>) : Closeable {

    private typealias MessageEventHandler<T>    = (args: MessageEvent<S, T, E>) -> Unit
    private typealias ChangedValueEventHandler  = (args: ChangedEvent<S, *, E>) -> Unit
    private typealias ErrorEventHandler         = (args: ErrorEvent<S, E>)      -> Unit
    private typealias AnyEventHandler           = (args: BasicEvent<S, E>)      -> Unit
    private typealias ErrorHandler              = (error: Throwable)            -> Unit
    private typealias ChangedValueEventHandlers = MutableMap<E, ChangedValueEventHandler>
    private typealias MessageEventHandlers      = MutableMap<Message, MessageEventHandler<*>>

    private val _subscription: Subscription

    private var _handleAnyEvent:             AnyEventHandler?          = null
    private var _handleAnyMessageEvent:      MessageEventHandler<*>?   = null
    private var _handleAnyChangedValueEvent: ChangedValueEventHandler? = null
    private var _handleChangedValueEvents:   ChangedValueEventHandlers = mutableMapOf()
    private var _handleError:                ErrorHandler?             = null
    private var _handleErrorEvent:           ErrorEventHandler?        = null
    private var _handleMessageEvents:        MessageEventHandlers      = mutableMapOf()

    init {

        _subscription =
            producer.events.onError {
                error ->

                _handleError?.invoke(error)
            }.subscribe {
                event ->

                when (event) {
                    is org.nexivo.kt.eventify.events.MessageEvent<S, *, E>
                        -> {
                        // todo, code debt can be optimized for certain scenarios with function pointer
                        (_handleMessageEvents[event.type] ?: _handleAnyMessageEvent ?: _handleAnyEvent)?.invoke(event)
                    }

                    is ChangedEvent<S, *, E>
                        -> {
                        // todo, code debt can be optimized for certain scenarios with function pointer
                        (_handleChangedValueEvents[event.event] ?: _handleAnyChangedValueEvent ?: _handleAnyEvent)?.invoke(event)
                    }

                    is ErrorEvent<S, E>
                        -> _handleErrorEvent?.invoke(event)

                    else -> _handleAnyEvent?.invoke(event)
                }
            }
    }

    fun onChangedValue(event: E? = null, handler: ChangedValueEventHandler?): EventConsumer<S, E> {

        if (handler != null) {
            if (event != null ) {
                if (_handleChangedValueEvents.containsKey(event)) {
                    throw IllegalArgumentException("A handler for $event has already been initialized!")
                }

                _handleChangedValueEvents.put(event, handler)
            } else {
                if (_handleAnyChangedValueEvent != null) {
                    throw IllegalArgumentException("A catch all event handler has already been initialized!")
                }

                _handleAnyChangedValueEvent = handler
            }
        } else {
            if (event != null) {
                _handleChangedValueEvents.remove(event)
            } else {
                _handleAnyChangedValueEvent = null
            }
        }

        return this
    }

    fun onError(handler: ErrorEventHandler?): EventConsumer<S, E> {

        _handleErrorEvent = handler

        return this
    }

    fun onException(handler: ErrorHandler): EventConsumer<S, E> {

        _handleError = handler

        return this
    }

    fun onEvent(handler: AnyEventHandler?): EventConsumer<S, E> {

        _handleAnyEvent = handler

        return this
    }

    fun <T: Message> onMessage(message: T, handler: MessageEventHandler<T>?): EventConsumer<S, E> {

        if (handler != null) {
            if (_handleMessageEvents.containsKey(message)) {
                throw IllegalArgumentException("A handler for $message has already been initialized!")
            }

            @Suppress("UNCHECKED_CAST")
            _handleMessageEvents.put(message, handler as MessageEventHandler<*>)
        } else {
            _handleMessageEvents.remove(message)
        }

        return this
    }

    override fun close() {

        _subscription.unsubscribe()
    }
}

