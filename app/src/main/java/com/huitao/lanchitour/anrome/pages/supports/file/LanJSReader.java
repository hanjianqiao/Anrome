package com.huitao.lanchitour.anrome.pages.supports.file;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by hanji on 2016/11/8.
 */

public class LanJSReader {
        public static String getFromAssets(Context m) {
            try {
                InputStreamReader inputReader = new InputStreamReader(m.getResources().getAssets().open("sys_h5/copy_token.js"));
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

