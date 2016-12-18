package com.smartdengg.jsonprinter;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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
        JsonPrinter.d("today", finalTodayJson);
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
