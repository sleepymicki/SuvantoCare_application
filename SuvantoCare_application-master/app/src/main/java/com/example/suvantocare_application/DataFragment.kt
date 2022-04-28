package com.example.suvantocare_application

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.suvantocare_application.databinding.FragmentDataBinding
import com.google.gson.GsonBuilder
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

class DataFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentDataBinding? = null

    var mqttAndroidClient: MqttAndroidClient? = null

    // use tcp://10.0.2.2:1883 if using local mosquitto broker
    val serverUri = "tcp://k2936e.messaging.internetofthings.ibmcloud.com:1883"
    // USE A UNIQUE CLIENT-ID. replace the word "tvtplab1234" with your own identifier
// for example, your student code
    var clientId = "a:k2936e:a1901311ab"
    val subscriptionTopic = "iot-2/type/esp32/id/esp666/evt/status1/fmt/json"
    val subscriptionMqttTopic = "iot-2/type/esp32/id/murata666/evt/status1/fmt/json"

    // username and password not needed if using local mosquitto broker
    val mqttUsername = "a-k2936e-d93gpt8bgx"
    val mqttPassword = "aXzuiVgrtOvb4uB+GY"

    companion object {
        private val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"
    }
    private fun getRandomString(sizeOfRandomString: Int): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun isNumericToX(toCheck: String): Boolean {
        return toCheck.toDoubleOrNull() != null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Create the client!
        //mqttAndroidClient = MqttAndroidClient(activity as Context, serverUri, clientId)
        mqttAndroidClient = MqttAndroidClient(context as Context, serverUri, clientId)

        // CALLBACKS, these will take care of the connection if something unexpected happen
        mqttAndroidClient?.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                    Log.d("ADVTECH", "reconnected to MQTT")
                    // we have to subscribe again because of the reconnection
                    subscribeToTopic(subscriptionTopic)
                    subscribeToTopic(subscriptionMqttTopic)

                } else {
                    Log.d("ADVTECH", "connected to MQTT")
                }
            }

            override fun connectionLost(cause: Throwable) {
                Log.d("ADVTECH", "MQTT connection lost")
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                // THIS VARIABLE IS THE JSON DATA. you can use GSON to get the needed
                // data (temperature for example) out of it, and show it in a textview or something else
                try {
                    val result = String(message.payload)
                    Log.d("ADVTECH", result)

                    val gson = GsonBuilder().setPrettyPrinting().create()

                    if(isNumericToX(result))
                    {

                        binding.mytemperatureviewtest.setTemperature(result.toFloat())
                        Log.d("ADVTECH", "Lämpötila")
                    }
                    else
                    {
                        var item : MuRata = gson.fromJson(result, MuRata::class.java)
                        var text = item.HR + "-" + item.Time
                        binding.muratadata.addData(text)
                        Log.d("ADVTECH", "MuRata")
                    }

                } catch (e: Exception) {
                    Log.d("ADVTECH", e.toString());
                }
            }

            // used when sending data via MQTT
            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })

        // CONNECT TO MQTT

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = true
        mqttConnectOptions.userName = mqttUsername
        mqttConnectOptions.password = mqttPassword.toCharArray()

        try {
            mqttAndroidClient?.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient!!.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic(subscriptionTopic)
                    subscribeToTopic(subscriptionMqttTopic)
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d("ADVTECH", "Failed to connect!")
                }
            })
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }

        return root
    }

    // subscriber method
    fun subscribeToTopic(topicToSubscribeTo : String) {
        try {
            mqttAndroidClient?.subscribe(topicToSubscribeTo, 0, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.d("ADVTECH", "Subscribed!")
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d("ADVTECH", "Failed to subscribe")
                }
            })
        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mqttAndroidClient?.close()
    }
}