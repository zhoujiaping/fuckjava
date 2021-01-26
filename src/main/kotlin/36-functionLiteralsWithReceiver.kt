fun task(): List<Boolean> {
    //函数字面量
    //A.(B)->C 给A类型添加一个函数，参数为B类型，返回值为C类型
    val isEven: Int.() -> Boolean = { this%2==0 }
    val isOdd: Int.() -> Boolean = { this%2==1 }

    return listOf(42.isOdd(), 239.isOdd(), 294823098.isEven())
}