package com.smarttoolfactory.core.ui.viewstate

class ViewState<T>(
    val status: Status,
    val data: List<T>? = null,
    val error: Throwable? = null
) {
    fun isLoading() = status == Status.LOADING

    fun getErrorMessage() = error?.message

    fun shouldShowErrorMessage() = error != null
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
