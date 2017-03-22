package com.huitao.lanchitour.anrome.pages.supports.http;

import android.net.SSLCertificateSocketFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by hanji on 2017/3/11.
 */

public class PostGetJson {
    private static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return true;
            }
        };
        return hostnameVerifier;
    }

    public static JSONObject httpsPostGet(String address, String jsonString) {

        try {
            URL url = new URL(address);
            //HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
            conn.setHostnameVerifier(getHostnameVerifier());

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = jsonString;

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String stringToParse = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                stringToParse += output;
                System.out.println(output);
            }
            conn.disconnect();
            JSONObject json = new JSONObject(stringToParse);
            return json;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject httpPostGet(String address, String jsonString) {

        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = jsonString;
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            String stringToParse = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                stringToParse += output;
                System.out.println(output);
            }
            conn.disconnect();
            JSONObject json = new JSONObject(stringToParse);
            return json;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
