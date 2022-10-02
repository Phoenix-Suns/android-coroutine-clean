package self.tranluunghia.mvicoroutine.core.entity

import androidx.annotation.Keep

@Keep
data class DataState<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val errorInfo: ErrorInfo?,
    val errorResponse: ErrorResponse?
) {
    enum class Status { SUCCESS, ERROR, LOADING }

    companion object {
        fun <T> success(data: T): DataState<T> = DataState(Status.SUCCESS, data, null, null, null)

        fun <T> error(
            message: String,
            data: T? = null,
            errorType: ErrorType,
            code: Int,
            exception: Throwable? = null,
            errorResponse: ErrorResponse? = null,
        ): DataState<T> =
            DataState(
                Status.ERROR,
                data,
                message,
                ErrorInfo(errorType, code, exception),
                errorResponse
            )

        fun <T> loading(data: T? = null): DataState<T> =
            DataState(Status.LOADING, data, null, null, null)

        fun <T> noInternet(): DataState<T> =
            DataState(
                Status.ERROR,
                null,
                null,
                ErrorInfo(ErrorType.NETWORK, -1, null),
                null
            )

        fun <T> unexpectedError(data: T? = null, e: Exception): DataState<T> =
            error(
                e.localizedMessage ?: "Unexpected error!",
                data,
                ErrorType.UNEXPECTED,
                -1,
                e
            )
    }
}

@Keep
data class ErrorInfo(
    val errorType: ErrorType,
    val code: Int,
    val exception: Throwable?
)

@Keep
enum class ErrorType {
    API,
    NETWORK,
    SERVER,
    UNEXPECTED
}

@Keep
data class ErrorResponse(
    val code: Int,
    val status: Int,
    val error: String?,
    val detail: String?,
    var message: String?
)