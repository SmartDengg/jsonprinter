package com.smartdengg.jsonprinter;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.smartdengg.androidjsonprinter.JsonPrinter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Timber.plant(new Timber.DebugTree());

    Timber.plant(new Timber.Tree() {
      @Override protected void log(int priority, String tag, String message, Throwable t) {
        Log.println(priority, tag, message);
      }
    });

    String cityJson = "";
    String todayJson = "";

    AssetManager assetManager = getAssets();

    try {
      InputStreamReader inputReader = new InputStreamReader(assetManager.open("city.json"));
      BufferedReader bufReader = new BufferedReader(inputReader);
      String line;
      while ((line = bufReader.readLine()) != null) cityJson += line;
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      InputStreamReader inputReader = new InputStreamReader(assetManager.open("today.json"));
      BufferedReader bufReader = new BufferedReader(inputReader);
      String line;
      while ((line = bufReader.readLine()) != null) todayJson += line;
    } catch (IOException e) {
      e.printStackTrace();
    }

    final String finalCityJson = cityJson;
    findViewById(R.id.city).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        JsonPrinter.d(finalCityJson);
      }
    });

    final String finalTodayJson = todayJson;
    findViewById(R.id.today).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        JsonPrinter.d("today", finalTodayJson, "打印URL地址");
      }
    });

    final Map<String, String> params = new HashMap<>(3);
    params.put("cityname", URLEncoder.encode("北京"));
    params.put("dtype", "json");
    params.put("key1", "f4817e5b8e43721a6fe7352bb60e27b2");
    params.put("key2", "f4817e5b8e43721a6fe7352bb60e27b2");
    params.put("key3", "f4817e5b8e43721a6fe7352bb60e27b2");
    params.put("key4", "f4817e5b8e43721a6fe7352bb60e27b2");
    params.put("key5", "f4817e5b8e43721a6fe7352bb60e27b2");

    findViewById(R.id.gson).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        JsonPrinter.d("gson", new Gson().toJson(params));
      }
    });

    findViewById(R.id.empty).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        JsonPrinter.d("empty", null);
      }
    });

    findViewById(R.id.terrible).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        JsonPrinter.d("terrible", "terrible terrible terrible terrible");
      }
    });
  }
}
