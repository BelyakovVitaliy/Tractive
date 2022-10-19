package com.tractive.aggregation

fun aggregate(
    products: List<String>,
    mapping: Map<String, String>
):Array<String> {
    return products.groupingBy { it }
        .eachCount()
        .mapNotNull {
            mapping[it.key]?.dropLastWhile { cr -> cr == ' ' || cr == '}' }?.plus(",\"quantity\": ${it.value}}")
        }.toTypedArray()
}