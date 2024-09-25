package owner.yacer.mynewsapp.api

import owner.yacer.mynewsapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private var client: Retrofit? = null
        fun getInstance(): Retrofit {
            if (client == null)
                client = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return client!!
        }
    }
}