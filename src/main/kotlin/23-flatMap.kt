// Return all products this customer has ordered
val Customer.orderedProducts: Set<Product> get() = orders.flatMap { it.products }.toSet()

// Return all products that were ordered by at least one customer
val Shop.allOrderedProducts: Set<Product> get() {
    TODO()
}