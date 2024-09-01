package com.example.notificationservice;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Model> modelList;
    private CustomListAdapter customListAdapter;
    private RecyclerView recyclerView;
    private FileUtils fileUtils;
    private ItemTouchHelper itemTouchHelper;
    private CustomSimpleCallBack customSimpleCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Set status bar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);
        checkNotificationAccessPermission();

        modelList = new ArrayList<>();
        customListAdapter = new CustomListAdapter(getApplicationContext(), modelList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customListAdapter);

        customSimpleCallBack = new CustomSimpleCallBack(modelList, customListAdapter);
        itemTouchHelper = new ItemTouchHelper(customSimpleCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        fileUtils = new FileUtils(getApplicationContext());
        LocalBroadcastManager.getInstance(this).registerReceiver(onReceive, new IntentFilter("MSG"));
        fileUtils.loadData("NotificationService.txt");
    }

    private void checkNotificationAccessPermission() {
        if (!Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {
            Button yesButton, noButton;
            Dialog dialog =  new Dialog(this);
            dialog.setContentView(R.layout.notification_access_permission_dialog);
            yesButton = dialog.findViewById(R.id.yesButton);
            noButton = dialog.findViewById(R.id.noButton);
            yesButton.setOnClickListener(view -> {
                startActivity(new Intent( Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                dialog.dismiss();
                checkFileStoragePermission();
            });

            noButton.setOnClickListener(view -> {
                dialog.dismiss();
                Toast.makeText(this, "Need notification access permission to run the app", Toast.LENGTH_LONG).show();
            });
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StringBuilder stringBuilder = new StringBuilder();
        for(Model model: modelList){
            String pack, ticker, title, text, actions;

            pack = model.getPack();
            title = model.getTitle();
            text = model.getText();
            ticker = model.getTicker();
            actions = model.getActions();

            String line = pack+","+ticker+","+title+","+text+","+actions+"\n";
            stringBuilder.append(line);
        }
        fileUtils.saveData(stringBuilder.toString(), "NotificationService.txt", false);
    }

    void checkFileStoragePermission(){
        Button yesButton, noButton;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(!Environment.isExternalStorageManager()){
                Dialog dialog =  new Dialog(this);
                dialog.setContentView(R.layout.all_file_access_permission_dialog);
                yesButton = dialog.findViewById(R.id.yesFileButton);
                noButton = dialog.findViewById(R.id.noFileButton);
                yesButton.setOnClickListener(view -> {
                    startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
                    dialog.dismiss();
                });
                noButton.setOnClickListener(view -> {
                    dialog.dismiss();
                    Toast.makeText(this, "Need all files access permission to run the app", Toast.LENGTH_LONG).show();
                });
                dialog.show();
            }
        }
    }

    BroadcastReceiver onReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String pack, ticker, title, text, actions;
            pack = intent.getStringExtra("pack");
            ticker = intent.getStringExtra("ticker");
            text = intent.getStringExtra("text");
            title = intent.getStringExtra("title");
            actions = intent.getStringExtra("actions");

            Model model = new Model(title, text, pack, ticker, actions);

            modelList.add(model);
            customListAdapter.notifyDataSetChanged();
        }
    };

}