package org.nexivo.kt.eventify.api

enum class MessagePriority(val level: Int) {

    Trace   (MessagePriority.TRACE),
    Debug   (MessagePriority.DEBUG),
    Info    (MessagePriority.INFO),
    Warn    (MessagePriority.WARN),
    Error   (MessagePriority.ERROR),
    Fatal   (MessagePriority.FATAL),
    Message (MessagePriority.MESSAGE);

    companion object {

        const val TRACE:   Int = 5000
        const val DEBUG:   Int = 10000
        const val INFO:    Int = 20000
        const val WARN:    Int = 30000
        const val ERROR:   Int = 40000
        const val FATAL:   Int = 50000
        const val MESSAGE: Int = MessagePriority.SILENCE - 1
        const val SILENCE: Int = Int.MAX_VALUE
    }
}