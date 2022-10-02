package self.tranluunghia.mvicoroutine.domain.model

data class Paging(
        var firstPage : Int = 1,
        var perPage : Int = 10,
        var reachEnd: Boolean = false
) {
    var page = firstPage

    fun reset() {
        page = 1
        reachEnd = false
    }

    fun isFirstPage() = page == firstPage

    constructor() : this(1, 10, false)
}