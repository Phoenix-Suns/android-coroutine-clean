package self.tranluunghia.mvicoroutine.core.basemvi

interface Mapper<in FROM, out TO> {
    fun map(from: FROM): TO
}