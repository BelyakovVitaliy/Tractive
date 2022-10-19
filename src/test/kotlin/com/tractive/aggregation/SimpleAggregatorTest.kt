package com.tractive.aggregation

import kotlin.test.Test
import kotlin.test.assertContentEquals

class SimpleAggregatorTest {

    @Test
    fun test() {
        val expectedResult = arrayOf(
            "{\"version\": 1,\"edition\": \"X\",\"quantity\": 2}",
            "{\"version\": 1,\"quantity\": 1}",
            "{\"version\": 2,\"edition\": \"Z\",\"quantity\": 1}"
        )
        val result = aggregate(
            listOf("CVCD", "CVCD", "DDDF", "SDFD"),
            mapOf(
                "CVCD" to "{\"version\": 1,\"edition\": \"X\"}",
                "SDFD" to "{\"version\": 2,\"edition\": \"Z\"}",
                "DDDF" to "{\"version\": 1}"
            )
        )

        assertContentEquals(expectedResult, result)
    }
}