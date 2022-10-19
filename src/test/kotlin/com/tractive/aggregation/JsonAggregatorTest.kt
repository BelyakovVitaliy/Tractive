package com.tractive.aggregation

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class JsonAggregatorTest {

    private fun getProducts() = listOf("CVCD", "SDFD", "DDDF", "SDFD")

    private fun getPurchases() = mapOf(
        "CVCD" to Purchase(1, "X"),
        "SDFD" to Purchase(2, "Z"),
        "DDDF" to Purchase(1),
    )

    @Test
    fun aggregateTest() {
        val expectedResult = arrayOf(
            AggregatedPurchase(1, "X", 1),
            AggregatedPurchase(2, edition = "Z", quantity = 2),
            AggregatedPurchase(version = 1, quantity = 1),
        )
        val result = aggregate(
            getProducts(),
            getPurchases()
        )

        assertContentEquals(expectedResult, result)
    }

    @Test
    fun aggregateToJsonTest() {
        val expectedResult =
            "[{\"version\":1,\"edition\":\"X\",\"quantity\":1},{\"version\":2,\"edition\":\"Z\",\"quantity\":2},{\"version\":1,\"quantity\":1}]"
        val result = aggregateToJson(
            getProducts(),
            getPurchases()
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun aggregateFromJsonToJsonTest() {
        val products = "[\"CVCD\", \"SDFD\", \"DDDF\", \"SDFD\"]"
        val purchases =
            "{\"CVCD\": {\"version\": 1,\"edition\": \"X\"},\"SDFD\": {\"version\": 2,\"edition\": \"Z\"},\"DDDF\": {\"version\": 1}}"
        val expectedResult =
            "[{\"version\":1,\"edition\":\"X\",\"quantity\":1},{\"version\":2,\"edition\":\"Z\",\"quantity\":2},{\"version\":1,\"quantity\":1}]"
        val result = aggregateFromJsonToJson(
            products,
            purchases
        )

        assertEquals(expectedResult, result)
    }
}