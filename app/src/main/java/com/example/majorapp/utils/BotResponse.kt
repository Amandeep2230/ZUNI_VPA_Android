package com.example.majorapp.utils

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.majorapp.utils.Constants.CALL
import com.example.majorapp.utils.Constants.OPEN_APPS
import com.example.majorapp.utils.Constants.OPEN_FLASHLIGHT
import com.example.majorapp.utils.Constants.OPEN_GOOGLE
import com.example.majorapp.utils.Constants.OPEN_SEARCH
import com.example.majorapp.utils.Constants.SCHEDULE_EVENT
import com.example.majorapp.utils.Constants.SEND_MAIL
import com.example.majorapp.utils.Constants.SET_ALARM
import com.example.majorapp.utils.Constants.SHOW_WEATHER

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when{

            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup?"
                    2 -> "Ola!"
                    else -> "error"
                }
            }

            //How are you
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I am doing fine, thanks for asking!"
                    1 -> "I am hungry"
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //Thank you
            message.contains("thank you") -> {
                when (random) {
                    0 -> "You're welcome!"
                    1 -> "I am glad that I could be of some help!"
                    2 -> "No problem!"
                    else -> "error"
                }
            }

            //flip coin
            message.contains("flip") && message.contains("coin") -> {
                var r = (0..1).random()
                val result = if(r==0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }


            //solve maths
            message.contains("solve") -> {
                val equation: String? = message.substringAfter("solve")

                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    answer.toString()
                }
                catch (e: Exception){
                    "Sorry, I can't solve that"
                }
            }

            //Gets current time
            (message.contains("what") || message.contains("tell") || message.contains("show")) && message.contains("time")  -> {
                Time.timeStamp()
            }

            //Flashlight
            message.contains("flashlight") || message.contains("torch")  -> {
                OPEN_FLASHLIGHT
            }

            message.contains("open")  -> {
                OPEN_APPS
            }

            message.contains("search") -> {
                OPEN_SEARCH
            }

            message.contains("call") || message.contains("dial") -> {
                CALL
            }

            message.contains("schedule") && message.contains("event") -> {
                SCHEDULE_EVENT
            }
            
            //setting alarm
            message.contains("set") && message.contains("alarm") -> {
                "Please enter time in 'HH:MM'"
            }
            
            message.contains(":") -> {
                SET_ALARM
            }

            //fetch weather
            message.contains("weather") -> {
                SHOW_WEATHER
            }

            //send mail
            (message.contains("send") || message.contains("compose")) && (message.contains("mail") || message.contains("email")) -> {
                SEND_MAIL
            }

            else -> {
                when (random) {
                    0 -> "I don't understand"
                    1 -> "IDK"
                    2 -> "Try asking me something different!"
                    else -> "error"
                }
            }
        }
    }

}