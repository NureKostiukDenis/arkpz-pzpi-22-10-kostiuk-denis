package org.anware.data.service

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

open class Logger {
    protected val logger: Log = LogFactory.getLog(javaClass)
}