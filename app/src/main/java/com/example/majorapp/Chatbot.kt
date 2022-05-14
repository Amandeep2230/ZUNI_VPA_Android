package com.example.majorapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.majorapp.data.Message
import com.example.majorapp.utils.BotResponse
import com.example.majorapp.utils.Constants.CALL
import com.example.majorapp.utils.Constants.OPEN_APPS
import com.example.majorapp.utils.Constants.OPEN_FLASHLIGHT
import com.example.majorapp.utils.Constants.OPEN_SEARCH
import com.example.majorapp.utils.Constants.RECEIVE_ID
import com.example.majorapp.utils.Constants.SEND_ID
import com.example.majorapp.utils.Constants.SEND_MAIL
import com.example.majorapp.utils.Constants.SET_ALARM
import com.example.majorapp.utils.Constants.SHOW_WEATHER
import com.example.majorapp.utils.Time
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.chatbot.*
import kotlinx.coroutines.*
import java.util.*


class Chatbot : AppCompatActivity() {
    private lateinit var adapter: MessagingAdapter
    //private val botList = listOf("ZUNI")
    private val REQUEST_CODE_SPEECH_INPUT = 1000
    private val req_call = 1
    var unname: String? = null
    var numbers: String? = null
    var texttv: EditText? = null
    var textToSpeech: TextToSpeech? = null
    lateinit var temp1: Array<String>
    private val READ_CONTACTS_PERMISSIONS_REQUEST = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbot)
        getPermissionToReadUserContacts()
        texttv = findViewById(R.id.et_message)
        recyclerView()

        clickEvents()
        mic_btn.setOnClickListener(){
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something....")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
            }
        }
        val random = (0..3).random()
        customMessage("Hello! Its ZUNI this side, how may I help you?")



        textToSpeech = TextToSpeech(applicationContext
        ) { i ->
            if (i == TextToSpeech.SUCCESS) {
                val lang = textToSpeech!!.setLanguage(Locale.ENGLISH)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> if (resultCode == RESULT_OK && null != data) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (!result.isNullOrEmpty()) {
                    val recogtext = result[0]
                    val message = recogtext.toString()
                    val timeStamp = Time.timeStamp()

                    if (message.isNotEmpty()) {
                        et_message.setText("")

                        adapter.insertMessage(com.example.majorapp.data.Message(message, SEND_ID, timeStamp))
                        rv_messages.scrollToPosition(adapter.itemCount - 1)

                        botResponse(message)
                    }
                }
            }
        }
    }


    private fun clickEvents(){
        btn_send.setOnClickListener {
            sendMessage()
        }

        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount - 1)
                }

            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()){
            et_message.setText("")

            adapter.insertMessage(com.example.majorapp.data.Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)


                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                when (response) {

                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                    OPEN_FLASHLIGHT -> {
                        var cameraId: String? = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            val camManager = getSystemService(CAMERA_SERVICE) as CameraManager
                            try {
                                cameraId = camManager.cameraIdList[0]
                                camManager.setTorchMode(cameraId, true) //Turn ON
                            } catch (e: CameraAccessException) {
                                e.printStackTrace()
                            }
                        }
                    }

                    OPEN_APPS -> {

                        val searchTerm: String? = message.substringAfterLast("open")
                        val temp = searchTerm.toString().split(" ", limit = 2)
                        unname = temp[1]
                        getAppName(temp[1])?.let { launchApp(it) }
                    }

                    CALL -> {

                        temp1 = message.split(" ".toRegex(), 2).toTypedArray()
                        makeCall(temp1[1].replace("[\\s\\-()]".toRegex(), ""))
                    }

                    SET_ALARM -> {
                        val hour: String? = message.substringBefore(":")
                        val minute: String? = message.substringAfter(":")

                        if (hour != null && minute != null) {
                            val alarm = Intent(AlarmClock.ACTION_SET_ALARM)
                            alarm.putExtra(AlarmClock.EXTRA_HOUR, hour.toInt())
                            alarm.putExtra(AlarmClock.EXTRA_MINUTES, minute.toInt())
                            startActivity(alarm)
                        }
                    }

                    SHOW_WEATHER -> {
                        fetch_weather()
                    }

                    SEND_MAIL -> {
                        val emailIntent = Intent(Intent.ACTION_SEND)
                        emailIntent.type = "text/plain"
                        startActivity(emailIntent)
                    }
            }

                val s: String = response.toString()
                val speech = textToSpeech!!.speak(s, TextToSpeech.QUEUE_FLUSH, null)
                }
            }
        }




    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun customMessage(message: String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(com.example.majorapp.data.Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    fun logout(view: View) {
        val session = Session(this@Chatbot)
        session.removeSession()
        val intent = Intent(this, login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    //method to get app name
    @SuppressLint("QueryPermissionsNeeded")
    fun getAppName(name: String): String? {
        var name = name
        name = name.toLowerCase()
        val pm = packageManager
        val l = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        for (ai in l) {
            val n = pm.getApplicationLabel(ai).toString().toLowerCase()
            if (n.contains(name) || name.contains(n)) {
                return ai.packageName
            }
        }
        return "package.not.found"
    }

    // method for launching an app
    protected fun launchApp(packageName: String) {
        val mIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (packageName == "package.not.found") {
            Toast.makeText(applicationContext, "I'm afraid, there's no such app!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=" + unname + "&c=apps")))
        } else if (mIntent != null) {
            try {
                startActivity(mIntent)
            } catch (err: java.lang.Exception) {
                Log.i("darkbot", "App launch failed!")
                Toast.makeText(this, "I'm afraid, there's no such app!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getNumber(name: String?, context: Context): String {
        val number = ""
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)
        val people = context.contentResolver.query(uri, projection, null, null, null)
                ?: return number
        val indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        people.moveToFirst()
        do {
            val Name = people.getString(indexName)
            val Number = people.getString(indexNumber)
            if (Name.equals(name, ignoreCase = true)) {
                return Number.replace("-", "")
            }
        } while (people.moveToNext())
        people.close()
        return number
    }

    // method for placing a call
    private fun makeCall(name: String) {
        try {
            if (ContextCompat.checkSelfPermission(this@Chatbot,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@Chatbot, arrayOf(Manifest.permission.CALL_PHONE), req_call)
            } else {
                val number: String
                number = if (name.toRegex().matches("-?\\d+(\\.\\d+)?") && name.length > 2) {
                    // string only contains number
                    name
                } else {
                    getNumber(name, this@Chatbot)
                }
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$number")
                startActivity(callIntent)
            }
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == req_call) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall(temp1.get(1))
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermissionToReadUserContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_CONTACTS)) {
            }
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    READ_CONTACTS_PERMISSIONS_REQUEST)
        }
    }

    //fetch weather details
     fun fetch_weather() {
        val get_weather = Intent(this, Weather::class.java)
        startActivity(get_weather)
    }
}