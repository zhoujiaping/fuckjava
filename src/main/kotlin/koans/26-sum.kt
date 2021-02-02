package koans

// Return the sum of prices of all products that a customer has ordered.
// Note: the customer may order the same product for several times.
fun Customer.getTotalOrderPrice(): Double = orders.sumByDouble { order->order.products.sumByDouble { it.price } }