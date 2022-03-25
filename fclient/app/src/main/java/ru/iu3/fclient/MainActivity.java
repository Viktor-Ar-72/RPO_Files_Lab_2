package ru.iu3.fclient;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.iu3.fclient.databinding.ActivityMainBinding;


import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Random;
//import java.io.*;

//import org.apache.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


import java.text.DecimalFormat;
import java.lang.Long;


//Добавлены в Lab_4_0
import org.apache.commons.io.IOUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements TransactionEvents{

    ActivityResultLauncher<Intent> activityResultLauncher;

    // Used to load the 'fclient' library on application startup.
    static {
        System.loadLibrary("fclient");
        System.loadLibrary("mbedcrypto");
    }

    private ActivityMainBinding binding;

    //Начало изменений Lab_3_0

    private String pin;

    @Override
    public String enterPIN (int ptc, String amount)
    {
        pin = new String();
        Intent it = new Intent(MainActivity.this, PinpadActivity.class);
        it.putExtra("ptc", ptc);
        it.putExtra("amount", amount);
        synchronized (MainActivity.this)
        {
            activityResultLauncher.launch(it);
            try {
                MainActivity.this.wait();
            } catch (Exception ex)
            {
                Log.println(Log.ERROR, "Mtlog", ex.getMessage());
                //todo: log error;
            }
        }
        return pin;
    }

    //Конец изменений lab_3_0

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int res = initRng();
        byte[] keys = randomBytes(16);
        //String str = new String(keys, "UTF-8");
        //String STR_1 = new String(keys, java.nio.charset.StandardCharsets.ISO_8859_1);
        //byte[] B = {0, 4, 55, 11};
        String STR_1 = new String(keys, StandardCharsets.US_ASCII);
        System.out.println("Инициализированный массив - " + STR_1);
        //System.out.println("Инициализированный массив - " + keys.toString());
        Random random = new Random();

        byte[] data = new byte[20];
        for (int i = 0; i < data.length; ++i) {
            data[i] = (byte) ((byte) random.nextInt() % 255);
        }
        String STR_2 = new String(data, StandardCharsets.US_ASCII);
        System.out.println("Инициализированный массив 2 - " + STR_2);

        //Пример шифрованя данных (отладчик + I/O вывод)
        byte[] encrypt_data = encrypt(keys, data);
        String STR_3 = new String(encrypt_data, StandardCharsets.US_ASCII);
        System.out.println("Зашифрованный массив - " + STR_3);

        // Пример дешифрования данных (отладчик + I/O вывод)
        byte[] decrypt_data = decrypt(keys, encrypt_data);
        String STR_4 = new String(decrypt_data, StandardCharsets.US_ASCII);
        System.out.println("Расшифрованный массив - " + STR_4);

        // Example of a call to a native method
        //TextView tv = binding.sampleText;
        //tv.setText(stringFromJNI());

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            //Intent intent = result.getData();
                            //String pin = intent.getStringExtra("pin");
                            //Toast.makeText(MainActivity.this, pin, Toast.LENGTH_SHORT).show();
                            Intent intent = result.getData();
                            pin = intent.getStringExtra("pin");
                            synchronized (MainActivity.this) {
                                MainActivity.this.notifyAll();
                            }
                        }
                    }
                }
                );

        //Начало изменений Lab_3_0



        //Конец изменений Lab_3_0
    }

    //Далее закомменченная область - старые первоначальные тесты кнопок
/*
    public void onButtonClick(View view) {
       //Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show();
       // Ключ для шифрование данных
        byte[] key = stringToHex("0123456789ABCDEF0123456789ABCDE0");
        // Шифрование и дешифрование данных
        byte[] encryptedData = encrypt(key, stringToHex("1234567891011127"));
        byte[] decryptData = decrypt(key, encryptedData);
        // На осноании байтового массива получаем строку как конвертирование HEX-а
        String s = new String(Hex.encodeHex(decryptData)).toUpperCase();
        // Выводим на экран Toast
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
}
*/

    public void onButtonClick(View view) {
       // Intent intent = new Intent(this, PinpadActivity.class);
        //startActivity(intent);
        // Вот здесь произошла замена: вместо startActivity поставил launch
        //activityResultLauncher.launch(intent);
/*
        //Начало редактирования Lab_3_0
//Было закоменчено в Lab_4_0


        new Thread(()-> {
            try {
                byte[] trd = stringToHex("9F0206000000000100");
                //boolean ok = transaction(trd);
                transaction(trd);
                //runOnUiThread(()-> {
              //      Toast.makeText(MainActivity.this, ok ? "ok" : "failed", Toast.LENGTH_LONG);
               // });
            } catch (Exception ex) {
                //System.out.println("OSHIBKA VIPOLNENIAY!");
                Log.println(Log.ERROR, "MTTLOg", ex.getMessage());
               // todo: log error
            }
        }).start();
*/
        //byte[] trd = stringToHex("9F0206000000000100");
        //transaction(trd);

        //Intent new_intent = new Intent();
        //activityResultLauncher.launch(new_intent);
        new Thread(()-> {
            try {
                testHttpClient();
            }
            catch (Exception ex) {
                Log.println(Log.ERROR, "MTTLOG_New", ex.getMessage());
            }
        }).start();
    }


    @Override
    public void transactionResult(boolean result) {
        runOnUiThread(()-> {
            Toast.makeText(MainActivity.this, result ? "TRANSACTION COMPLETE" : "ERROR: Transaction did not complete", Toast.LENGTH_LONG).show();
        });
    }

    // Метод осуществляет конвертирование из String в HEX
    public static byte[] stringToHex(String s) {
        byte[] hex;
        try {
            hex = Hex.decodeHex(s.toCharArray());
        } catch (DecoderException decoderException) {
            // При возникновении ошибок выводим в LogCat сообщение об ошибке
            Log.println(Log.ERROR, "MtLog", decoderException.getMessage());
            hex = null;
        }

        return hex;
    }

    //Конец изменений Lab_3_0

    //Начало изменений Lab_4_0

    protected void testHttpClient()
    {
        new Thread(() -> {
            try {
                HttpURLConnection uc = (HttpURLConnection)
                        //(new URL("https://www.wikipedia.org").openConnection());
                        //(new URL("https://www.gosuslugi.ru").openConnection());
                          //(new URL("https://www.yandex.ru").openConnection());
                        (new URL("http://10.0.2.2:8081/api/v1/title").openConnection());
                InputStream inputStream = uc.getInputStream();
                String html = IOUtils.toString(inputStream);
                String title = getPageTitle(html);
                if (title.equals("Портал государственных услуг Российской Федерации"))
                {
                    runOnUiThread(() -> {
                        Toast.makeText(this, title + " - Это фу сайт)", Toast.LENGTH_LONG).show();
                    });
                }
                else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, title + " - Это хороший сайт", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (Exception ex) {
                Log.e("fapptag", "Http client fails", ex);
            }
        }).start();
    }

    /* Это первоначальная версия вызовы getPageTitle (просто заглушка)
    protected String getPageTitle(String html)
    {
        return "WWW";
    }

    //Версия с считыванием строки через позиции
    protected String getPageTitle(String html)
    {
        int pos = html.indexOf("<title");
        String p="not found";
        if (pos >= 0)
        {
            int pos2 = html.indexOf("<", pos + 1);
            if (pos >= 0)
                p = html.substring(pos + 7, pos2);
        }
        return p;
    }

     */

    //Версия с использованием решулярных выражений
    protected String getPageTitle(String html)
    {
        Pattern pattern = Pattern.compile("<title>(.+?)</title>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String p;
        if (matcher.find())
            p = matcher.group(1);
        else
            p = "Not found";
        return p;
    }
    //Конец изменений Lab_4_0
    /**
     * A native method that is implemented by the 'fclient' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public static native int initRng();
    public static native byte[] randomBytes(int no);

    public static native byte[] encrypt(byte[] key, byte[] data);
    public static native byte[] decrypt(byte[] key, byte[] data);

    // Начало изменений Lab_3_0
    public native boolean transaction (byte[] trd);
    // Конец изменений Lab_3_0
}