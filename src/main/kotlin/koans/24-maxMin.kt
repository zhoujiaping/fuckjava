package koans

// Return a customer whose order count is the highest among all customers
/*fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxWith(
    Comparator<Customer> { o1, o2 ->
        o1.orders.size - o2.orders.size
    })*/
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxBy { it.orders.size }


// Return the most expensive product which has been ordered
fun Customer.getMostExpensiveOrderedProduct(): Product? = orders.flatMap { it.products }.maxBy {
    it.price
}