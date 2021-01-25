// Return true if all customers are from the given city
fun Shop.checkAllCustomersAreFrom(city: City): Boolean = this.customers.all{
    it.city.name==city.name
}

// Return true if there is at least one customer from the given city
fun Shop.hasCustomerFrom(city: City): Boolean = this.customers.any{
    it.city.name == city.name
}

// Return the number of customers from the given city
fun Shop.countCustomersFrom(city: City): Int = this.customers.count{
    it.city.name==city.name
}

// Return a customer who lives in the given city, or null if there is none
fun Shop.findAnyCustomerFrom(city: City): Customer? = this.customers.find { it.city.name==city.name }