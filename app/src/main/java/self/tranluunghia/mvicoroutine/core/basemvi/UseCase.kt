package self.tranluunghia.mvicoroutine.core.basemvi

interface UseCase<in PARAMS, out TYPE> {
    operator fun invoke(params: PARAMS): TYPE
}