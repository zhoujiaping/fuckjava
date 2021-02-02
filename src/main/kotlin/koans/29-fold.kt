package koans

// Return the set of products that were ordered by every customer
fun Shop.getSetOfProductsOrderedByEveryCustomer(): Set<Product> {
    return when{
        customers.isEmpty() -> emptySet()
        else -> customers.fold(customers[0].orders.flatMap { it.products }.toSet()) { ps, c ->
            ps.intersect(c.orders.flatMap { it.products })
        }.toSet()
    }
}

