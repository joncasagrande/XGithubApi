package com.jonathan.data.utils

data class ErrorCode(
    val errorCode: Code = Code.UNKNOWN,
) {
    var exception: Throwable? = null

    constructor(errorMessage: String, errorCode: Code = Code.UNKNOWN) : this(errorCode) {
        this.exception = Throwable(errorMessage)
    }

    enum class Code(
        val errorCode: Int,
    ) {
        NOT_MODIFIED(304),
        INVALID_REQUEST(400),
        AUTHORIZED_ERROR(401), // there was a problem with the authorization, e.g. no token found or invalid
        UNAUTHORIZED(403), // the user is not authorized to perform a certain action
        NOT_FOUND(404), // The server can not find the requested resource
        CONFLICT(409), // request conflict with the current state of the target resource
        GONE(410), // requested content has been permanently deleted from server
        DATA_NOT_FOUND(422), // requested data not found
        UNKNOWN_SERVER_ERROR(500), // there was some problem with the server
        UNKNOWN(-1),
    }

    override fun toString() = "errorCode=$errorCode, exception=$exception."
}

fun Int.toErrorCode() =
    enumValues<ErrorCode.Code>().find { this == it.errorCode } ?: ErrorCode.Code.UNKNOWN
