package com.lanchitour.android.taobrokerage.javascritp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hanji on 2016/11/8.
 */

public class LanJS {
        public static String getFromAssets(Context m) {
            try {
                InputStreamReader inputReader = new InputStreamReader(m.getResources().getAssets().open("LanJs.js"));
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line = "";
                String Result = "";
                while ((line = bufReader.readLine()) != null)
                    Result += line;
                return Result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
}

