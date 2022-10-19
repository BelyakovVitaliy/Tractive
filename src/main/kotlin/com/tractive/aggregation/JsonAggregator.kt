package com.tractive.aggregation

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

data class Purchase(val version: Int, val edition: String? = null)

data class AggregatedPurchase(val version: Int, val edition: String? = null, val quantity: Int)

val gson: Gson = GsonBuilder().create()

fun aggregate(
    products: List<String>,
    purchases: Map<String, Purchase>
): Array<AggregatedPurchase> {
    return products.groupingBy { it }
        .eachCount()
        .mapNotNull {
            purchases[it.key]?.let { purchase -> AggregatedPurchase(purchase.version, purchase.edition, it.value) }
        }.toTypedArray()
}

fun aggregateToJson(
    products: List<String>,
    purchases: Map<String, Purchase>
): String {
    return gson.toJson(aggregate(products, purchases))
}

fun aggregateFromJsonToJson(
    productsJson: String,
    purchasesJson: String
): String {
    val listOfStringType = object : TypeToken<List<String>>() {}.type
    val products = gson.fromJson<List<String>>(productsJson, listOfStringType)
    val mapOfPurchasesType = object : TypeToken<Map<String, Purchase>>() {}.type
    val purchases = gson.fromJson<Map<String, Purchase>>(purchasesJson, mapOfPurchasesType)
    return aggregateToJson(products, purchases)
}