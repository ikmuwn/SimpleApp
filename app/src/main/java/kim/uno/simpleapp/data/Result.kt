package kim.uno.simpleapp.data

sealed class Result<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {

    class Progress<T> : Result<T>()
    class Success<T>(data: T?) : Result<T>(data = data)
    class Error<T>(data: T? = null, throwable: Throwable? = null) :
        Result<T>(data = data, throwable = throwable)

    override fun toString(): String {
        return when (this) {
            is Progress -> "Progress"
            is Success -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
        }
    }

}