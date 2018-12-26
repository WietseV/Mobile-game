package be.ucll.dirkfalls.utils

interface AsyncHandler<in SuccessType> {
    fun success(data: SuccessType)
    fun error(t: Throwable)
}