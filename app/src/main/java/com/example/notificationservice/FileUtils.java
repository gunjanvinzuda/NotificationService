package com.example.notificationservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtils {
    private static final String TAG = "NotificationService";
    Context context;

    public FileUtils(Context context) {
        this.context = context;
    }

    public void saveData(String data, String filename, boolean append){
        Log.i(TAG, "Save data");
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/download");
        dir.mkdir();
        File file = new File(root.getAbsolutePath() + "/download/" + filename);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            printWriter.write(data);
            printWriter.flush();
            printWriter.close();
            fileOutputStream.close();
        }catch (FileNotFoundException e){
            Log.i(TAG, "File not found exception." + e.getMessage());
        }catch (IOException e){
            Log.i(TAG, "IOException exception." + e.getMessage());
        }
    }

    public void loadData(String filename){
        Log.i(TAG, "Load data");
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/download");
        dir.mkdir();
        File file = new File(root.getAbsolutePath() + "/download/" + filename);
        String readLine;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while((readLine = bufferedReader.readLine()) != null){
                sendMsg(readLine);
            }
        }catch (FileNotFoundException e){
            Log.i(TAG, "File not found exception." + e.getMessage());
        }catch (IOException e){
            Log.i(TAG, "IOException exception." + e.getMessage());
        }
    }

    public void sendMsg(String readLine) {
        Log.i(TAG, readLine);
        String[] data = readLine.split(",", 5);

        Intent intent = new Intent("MSG");
        intent.putExtra("pack", data[0]);
        intent.putExtra("ticker", data[1]);
        intent.putExtra("title", data[2]);
        intent.putExtra("text", data[3]);
        intent.putExtra("actions", data[4]);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
