package org.anware.domain.entity
import java.sql.Time
import java.time.LocalDateTime
import java.time.ZoneOffset

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
