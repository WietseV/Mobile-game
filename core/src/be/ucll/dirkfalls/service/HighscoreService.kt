package be.ucll.dirkfalls.service

import be.ucll.dirkfalls.utils.AsyncHandler
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.HttpRequestBuilder
import com.google.gson.Gson


class HighscoreService {
    fun all(handler: AsyncHandler<List<HighscoreEntry>>) {
        val request = HttpRequestBuilder()
                .newRequest()
                .method(Net.HttpMethods.GET)
                .header("content-type", "application/json")
                .url("http://robin-vandenbroeck.sb.uclllabs.be:8080/")
                .build()

        Gdx.net.sendHttpRequest(request, object : Net.HttpResponseListener {
            override fun handleHttpResponse(httpResponse: Net.HttpResponse) {
                val gson = Gson()
                val responseString = httpResponse.resultAsString
                val array = gson.fromJson(responseString, Array<HighscoreEntry>::class.java)
                handler.success(array.asList())
            }

            override fun failed(t: Throwable) {
                handler.error(t)
            }

            override fun cancelled() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    fun create(name: String, score: Int, handler: AsyncHandler<HighscoreEntry>) {
        val entry = HighscoreDto(name, score)
        val gson = Gson()
        val json = gson.toJson(entry)

        val request = HttpRequestBuilder()
                .newRequest()
                .method(Net.HttpMethods.POST)
                .content(json)
                .header("content-type", "application/json")
                .url("http://robin-vandenbroeck.sb.uclllabs.be:8080/")
                .build()


        Gdx.net.sendHttpRequest(request, object : Net.HttpResponseListener {
            override fun handleHttpResponse(httpResponse: Net.HttpResponse) {
                val responseString = httpResponse.resultAsString
                handler.success(gson.fromJson(responseString, HighscoreEntry::class.java))
            }

            override fun failed(t: Throwable) {
                handler.error(t)
            }

            override fun cancelled() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}