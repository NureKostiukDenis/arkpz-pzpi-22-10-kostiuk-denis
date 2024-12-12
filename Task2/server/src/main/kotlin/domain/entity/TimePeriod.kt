package org.anware.domain.entity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.sql.Time

data class TimePeriod(
    val startTime: Time,
    val endTime: Time
){
    override fun toString(): String {
        return "${startTime.toLocalTime().hour}:${startTime.toLocalTime().minute}-${endTime.toLocalTime().hour}:${endTime.toLocalTime().minute}"
    }
}
fun toUnixTimestamp(localDateTime: LocalDateTime): Int {
    return localDateTime.toEpochSecond(ZoneOffset.UTC).toInt()
}
