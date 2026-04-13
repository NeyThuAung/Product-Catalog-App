package com.neythuaung.product_catalog_app.core.data

import android.util.Log
import com.neythuaung.product_catalog_app.core.domain.Error
import com.neythuaung.product_catalog_app.core.domain.Result
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLException

@Singleton
class SafeApiCall @Inject constructor() {

    suspend operator fun <T> invoke(
        apiCall: suspend () -> Response<T>
    ): Result<T, Error> {
        return try {
            val response = apiCall()
            Log.d("SafeApiCall", "Response: $response")

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(
                        Error.Remote(
                            code = response.code(),
                            message = "Empty response body"
                        )
                    )
                }
            } else {
                val errorBody = response.errorBody()?.string()

                Log.d(
                    "SafeApiCall",
                    "Error Code: ${response.code()}, Error Body: $errorBody"
                )

                val message = extractMessage(errorBody)

                Result.Error(
                    Error.Remote(
                        code = response.code(),
                        message = message
                    )
                )
            }

        } catch (e: UnknownHostException) {
            Log.d("SafeApiCall", "Network Error: $e")
            Result.Error(Error.Network)

        } catch (e: SocketTimeoutException) {
            Log.d("SafeApiCall", "Timeout Error: $e")
            Result.Error(Error.Timeout)

        } catch (e: SSLException) {
            Log.d("SafeApiCall", "SSL Error: $e")
            Result.Error(Error.Server)

        } catch (e: IOException) {
            Log.d("SafeApiCall", "IO Error: $e")
            Result.Error(Error.Network)

        } catch (e: Exception) {
            Log.d("SafeApiCall", "Unknown Error: $e")
            Result.Error(Error.Unknown)
        }
    }

    private fun extractMessage(errorBody: String?): String? {
        return try {
            errorBody
                ?.substringAfter("\"message\":\"")
                ?.substringBefore("\"")
        } catch (e: Exception) {
            null
        }
    }
}