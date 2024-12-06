package org.anware.domain.entity

import java.sql.Time

data class TimePeriod(
    val startTime: Time,
    val endTime: Time
){
    override fun toString(): String {
        return "${startTime.toLocalTime().hour}:${startTime.toLocalTime().minute}-${endTime.toLocalTime().hour}:${endTime.toLocalTime().minute}"
    }
}
