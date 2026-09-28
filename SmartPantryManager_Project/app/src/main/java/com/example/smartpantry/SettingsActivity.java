package com.example.smartpantry;
import android.os.*;import android.widget.*;import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity extends AppCompatActivity{@Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_settings);Switch s=findViewById(R.id.switchExpiry);s.setOnCheckedChangeListener((button,checked)->Toast.makeText(this,checked?"Expiry alerts enabled":"Expiry alerts disabled",Toast.LENGTH_SHORT).show());}}
