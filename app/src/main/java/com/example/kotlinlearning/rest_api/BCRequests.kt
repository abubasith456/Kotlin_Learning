package com.example.kotlinlearning.rest_api

import android.net.Uri
import com.example.kotlinlearning.rest.BCRestService
import com.example.kotlinlearning.rest.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BCRequests() {
    public val bcRestService: BCRestService
    private val bcRetrofit: Retrofit

    //    public ErrorPojo parseError(retrofit2.Response<?> response) {
    val bCRestService: BCRestService
        get() = bcRestService

    //        Converter<ResponseBody, ErrorPojo> converter =
    //                bcRetrofit.responseBodyConverter(ErrorPojo.class, new Annotation[0]);
    //        ErrorPojo error;
    //        try {
    //            if(response.errorBody() != null)
    //                error = converter.convert(response.errorBody());
    //            else
    //                return new ErrorPojo();
    //        } catch (IOException e) {
    //            return new ErrorPojo();
    //        }
    //        return error;
    //    }
    companion object {
        var instance: BCRequests? = null
            get() {
                if (field == null) {
                    field = BCRequests()
                }
                return field
            }
            private set
        private const val ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%"
        private val httpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(250, TimeUnit.SECONDS)
            .writeTimeout(250, TimeUnit.SECONDS)
    }

    init {
        bcRetrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(Uri.encode(BuildConfig.URl, ALLOWED_URI_CHARS))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bcRestService = bcRetrofit.create(BCRestService::class.java)
    }
}