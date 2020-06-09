package com.ivanwooll.sockettester

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringValidationUnitTest {

    @Test
    fun validSocketUrlPasses() {
        assertTrue("ws://1.1.1.1:3000".isValidWebSocketUrl())
        assertTrue("wss://1.1.1.1:30000".isValidWebSocketUrl())
    }
}