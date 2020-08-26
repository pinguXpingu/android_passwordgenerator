package com.pinguX.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button genButton=findViewById(R.id.button);
        Button copyButton=findViewById(R.id.button2);
        final EditText txt=(EditText)findViewById(R.id.editText);
        final NumberPicker picker = findViewById(R.id.numPick);
        final RadioGroup selected=findViewById(R.id.radioGroup);

        picker.setMinValue(8);
        picker.setMaxValue(32);

        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId=selected.getCheckedRadioButtonId();
                RadioButton r=(RadioButton)findViewById(selectedId);

                String s=(String)r.getText();
                int i=(Integer)picker.getValue();

                txt.setText(generatePassword(s,i));
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd=txt.getText().toString();
                ClipboardManager clip=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData pwdClip=ClipData.newPlainText("text",pwd);
                clip.setPrimaryClip(pwdClip);
            }
        });

    }
    private String generatePassword(String s, int i){
        final char[] nums="0123456789".toCharArray();
        final char[] alphas="abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
        final char[] others="\"!'^+%&/()=?_<>#$6{}[]\\-*".toCharArray();

        SecureRandom rnd=new SecureRandom();

        StringBuilder passwd=new StringBuilder();
        StringBuilder ary=new StringBuilder();

        if(s.equals("Numeric")) {
            ary.append(nums);
        }
        else if(s.equals("AlphaNumeric")) {
            ary.append(nums);
            ary.append(alphas);
        }
        else if(s.equals("AllPrintables")) {
            ary.append(nums);
            ary.append(alphas);
            ary.append(others);
        }
        char[] all=ary.toString().toCharArray();

        for (int j = 0; j < i; j++) {
            passwd.append(all[rnd.nextInt(all.length)]);
        }
        passwd.insert(rnd.nextInt(passwd.length()), all[rnd.nextInt(all.length)]);

        return passwd.toString();
    }
}
