package com.ivanwooll.sockettester

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private val okHttpClient by lazy { OkHttpClient() }
    private val tag = "****-"
    lateinit var ws: WebSocket

    private var serverConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        button.setOnClickListener {

            if (serverConnected) {
                ws.cancel()
            } else {
                val input = editTextServerUrl.text.toString()
                if (!input.isValidWebSocketUrl()) {
                    Toast.makeText(this, "Invalid WebSocket server url", Toast.LENGTH_LONG).show()
                } else {
                    val request: Request = Request.Builder().url(input).build()
                    ws = okHttpClient.newWebSocket(request, object : WebSocketListener() {
                        override fun onOpen(webSocket: WebSocket, response: Response) {
                            serverConnected = true
                            println("$tag open")
                            runOnUiThread {
                                button.text = "DISCONNECT"
                                writeOutput("Socket opened")
                            }
                        }

                        override fun onMessage(webSocket: WebSocket, text: String) {
                            println("$tag on message:$text")
                            runOnUiThread {
                                writeOutput(text)
                            }
                        }

                        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                            println("$tag closing: reason=$reason")
                        }

                        override fun onFailure(
                            webSocket: WebSocket,
                            t: Throwable,
                            response: Response?
                        ) {
                            println("$tag onFailure: ${t.message}")
                            t.printStackTrace()
                            runOnUiThread {
                                writeOutput(t.message ?: "")
                                button.text = "CONNECT"
                            }
                            serverConnected = false
                        }
                    })
//                    okHttpClient.dispatcher.executorService.shutdown();
                }
            }


        }
    }

    private fun writeOutput(message: String) {
        textViewOutput.append("$message\n")
    }
}