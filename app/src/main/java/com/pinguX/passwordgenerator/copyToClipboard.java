package com.pinguX.passwordgenerator;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class copyToClipboard extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClipboardManager clipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        Intent intent = getIntent();
        CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT);

        ClipData icerik = ClipData.newPlainText("",text);
        clipMan.setPrimaryClip(icerik);

        Toast.makeText(this, "Kopyalandı", Toast.LENGTH_SHORT).show();
        finish();
    }
}