// Return customers who have more undelivered orders than delivered
fun Shop.getCustomersWithMoreUndeliveredOrdersThanDelivered(): Set<Customer> = customers.filter {
    val (delivered,undelivered) = it.orders.partition { order->order.isDelivered }
    undelivered.size > delivered.size
}.toSet()