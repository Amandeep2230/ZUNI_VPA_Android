package com.example.majorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Homepage extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static final int REQUEST_CALL = 1;
    TextView texttv;
    ImageButton button;
    String numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        texttv = findViewById(R.id.texttv);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }

        });
    }

    public void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something....");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String cameraId = null;
        switch(requestCode){
            case REQUEST_CODE_SPEECH_INPUT:
                if(resultCode==RESULT_OK && null!=data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (result.get(0).contains("open") || result.get(0).contains("Open")) {
                        if (result.get(0).contains("youtube") || result.get(0).contains("YouTube") || result.get(0).contains("Youtube")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                            if (launchIntent != null) {
                                startActivity(launchIntent);
                            }
                        } else if (result.get(0).contains("gmail") || result.get(0).contains("Gmail")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                            if (launchIntent != null) {
                                startActivity(launchIntent);//null pointer check in case package name was not found
                            }
                        } else if (result.get(0).contains("whatsapp") || result.get(0).contains("WhatsApp") || result.get(0).contains("Whatsapp")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("snapchat") || result.get(0).contains("Snapchat") || result.get(0).contains("SnapChat")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.snapchat.android");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("telegram") || result.get(0).contains("Telegram") || result.get(0).contains("TeleGram")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.telegram.messenger");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("flipkart") || result.get(0).contains("Flipkart") || result.get(0).contains("FlipKart")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.flipkart.android");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("facebook") || result.get(0).contains("Facebook") || result.get(0).contains("FaceBook")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("amazon") || result.get(0).contains("Amazon") || result.get(0).contains("AmaZon")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.amazon.mShop.android.shopping");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("MX Player") || result.get(0).contains("mx player") || result.get(0).contains("Mx Player")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.mxtech.videoplayer.ad");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("facebook lite") || result.get(0).contains("Facebook lite") || result.get(0).contains("Facebook Lite")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.lite");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Paytm") || result.get(0).contains("paytm") || result.get(0).contains("PayTm")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("temple run") || result.get(0).contains("Temple run") || result.get(0).contains("Temple Run")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.imangi.templerun");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("zoom") || result.get(0).contains("ZOOM") || result.get(0).contains("Zoom")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("us.zoom.videomeetings");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("amazon prime video") || result.get(0).contains("Prime video") || result.get(0).contains("Amazon Prime")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.amazon.avod.thirdpartyclient");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("netflix") || result.get(0).contains("Netflix") || result.get(0).contains("NetFlix")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.netflix.mediaclient");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("zomato") || result.get(0).contains("Zomato") || result.get(0).contains("ZOMATO")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.application.zomato");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("swiggy") || result.get(0).contains("Swiggy") || result.get(0).contains("SWIGGY")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.swiggy.android");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Ludo King") || result.get(0).contains("ludo king") || result.get(0).contains("Ludo king")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ludo.king");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("candy crush") || result.get(0).contains("Candy crush") || result.get(0).contains("Candy Crush")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.king.candycrushsaga");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("free fire") || result.get(0).contains("Free fire") || result.get(0).contains("Free Fire")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.dts.freefireth");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("PUBG") || result.get(0).contains("pubg") || result.get(0).contains("PUB-G")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.tencent.ig");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("subway surfer") || result.get(0).contains("Subway surfer") || result.get(0).contains("Subway Surfer")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.kiloo.subwaysurf");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("hotstar") || result.get(0).contains("Hotstar") || result.get(0).contains("HotStar")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.startv.hotstaronly");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("among us") || result.get(0).contains("Among us") || result.get(0).contains("Among Us")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.innersloth.spacemafia");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("discord") || result.get(0).contains("Discord") || result.get(0).contains("DISCORD")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.discord");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Truecaller") || result.get(0).contains("truecaller") || result.get(0).contains("TrueCaller")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.truecaller");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Google") || result.get(0).contains("google") || result.get(0).contains("GOOGLE")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Google chrome") || result.get(0).contains("google chrome") || result.get(0).contains("Google Chrome")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("instagram") || result.get(0).contains("Instagram") || result.get(0).contains("InstaGram")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("messenger") || result.get(0).contains("Messenger") || result.get(0).contains("MESSENGER")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.orca");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("google photos") || result.get(0).contains("Google photos") || result.get(0).contains("Google Photos")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.photos");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Phone pe") || result.get(0).contains("phone pe") || result.get(0).contains("Phone Pe")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.phonepe.app");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Gmail") || result.get(0).contains("gmail") || result.get(0).contains("GMail")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Contacts") || result.get(0).contains("contacts") || result.get(0).contains("CONTACTS")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.contacts");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Applock") || result.get(0).contains("applock") || result.get(0).contains("Applock")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.domobile.applockwatcher");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Google Drive") || result.get(0).contains("google drive") || result.get(0).contains("Google drive")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.docs");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("clock") || result.get(0).contains("Clock") || result.get(0).contains("CLOCK")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.deskclock");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("Myntra") || result.get(0).contains("myntra") || result.get(0).contains("MynTra")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.myntra.android");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("aarogya setu") || result.get(0).contains("Aarogya Setu") || result.get(0).contains("Aarogya setu")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("nic.goi.aarogyasetu");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("messages") || result.get(0).contains("MESSAGES") || result.get(0).contains("Messages")) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.messaging");
                            try {
                                if (launchIntent != null) {
                                    startActivity(launchIntent);//null pointer check in case package name was not found
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Application not installed", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.get(0).contains("flashlight") || result.get(0).contains("Flashlight") || result.get(0).contains("Torch") || result.get(0).contains("torch") || result.get(0).contains("flash") || result.get(0).contains("Flash") && result.get(0).contains("on") || result.get(0).contains("On")) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                                try {
                                    cameraId = camManager.getCameraIdList()[0];
                                    camManager.setTorchMode(cameraId, true);   //Turn ON
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            texttv.setText(result.get(0));
                        }
                    }
                    else if(result.get(0).contains("Call")|| result.get(0).contains("call")){
                        numbers = result.get(0).replaceAll("[^0-9]", "");
                        makePhoneCall();
                    }
                    else{
                        texttv.setText(result.get(0));
                    }
                }
                break;
        }

    }

    private void makePhoneCall() {
        try {
            if (ContextCompat.checkSelfPermission(Homepage.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Homepage.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+numbers));
                startActivity(intent);
            }
        }
        catch(Exception e){
            Toast.makeText(this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void logout(View view) {
        Session session = new Session(Homepage.this);
        session.removeSession();
        Intent intent = new Intent(Homepage.this,login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}