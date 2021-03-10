package kim.uno.simpleapp.data

sealed class Result<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {

    class Success<T>(data: T?) : Result<T>(data = data)
    class Error<T>(data: T? = null, throwable: Throwable? = null) :
        Result<T>(data = data, throwable = throwable)

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
        }
    }

    fun success(unit: (T) -> Unit): Result<T> {
        if (this is Success) unit(data!!)
        return this
    }

    fun error(unit: (T?, Throwable?) -> Unit) : Result<T> {
        if (this is Error) unit(data, throwable)
        return this
    }

}