package com.pinguX.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

        picker.setMinValue(4);
        picker.setMaxValue(16);

        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId=selected.getCheckedRadioButtonId();
                RadioButton r=(RadioButton)findViewById(selectedId);

                if(selectedId==-1){
                    Context contxt=getApplicationContext();
                    CharSequence msg="Select password type";
                    int duration= Toast.LENGTH_SHORT;

                    Toast t=Toast.makeText(contxt,msg,duration);
                    t.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
                    t.show();
                }
                else{
                    String s=(String)r.getText();
                    int i=(Integer)picker.getValue();
                    txt.setText(generatePassword(s,i));
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd=txt.getText().toString();
                ClipboardManager clip=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData pwdClip=ClipData.newPlainText("text",pwd);
                clip.setPrimaryClip(pwdClip);

                Context contxt=getApplicationContext();
                CharSequence msg=pwd+" copied";
                int duration= Toast.LENGTH_SHORT;

                Toast t=Toast.makeText(contxt,msg,duration);
                t.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
                t.show();
            }
        });

    }
    private String generatePassword(String s, int i){
        final char[] nums="0123456789".toCharArray();
        final char[] alphas="abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
        final char[] others="!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".toCharArray();

        SecureRandom rnd=new SecureRandom();

        StringBuilder passwd=new StringBuilder();
        StringBuilder ary=new StringBuilder();

        if(s.equals("Numeric")) {
            ary.append(nums);
        }
        else if(s.equals("AlphaNumeric")) {
            ary.append(nums);
            ary.append(alphas);
            ary.append(nums);
        }
        else if(s.equals("AllPrintables")) {
            ary.append(nums);
            ary.append(alphas);
            ary.append(others);
            ary.append(nums);
        }
        char[] all=ary.toString().toCharArray();

        char x;

        while (passwd.length()<i) {
            x=all[rnd.nextInt(all.length)];

            //if char exists do not append it
            if(passwd.indexOf(String.valueOf(x))!=-1) {
                continue;
            }
            else {
                passwd.append(x);
            }
        }

        //contain at least one number
        if(s.equals("AlphaNumeric")) {
            int c=0;
            //check:
            for (char P : passwd.toString().toCharArray()) {
                for (char N : nums) {
                    if(String.valueOf(P).equals(String.valueOf(N))) {
                        c+=1;
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            if(c==0) {
                int a=rnd.nextInt(passwd.length());
                char b=nums[rnd.nextInt(nums.length)];

                passwd.deleteCharAt(passwd.length()-1);
                passwd.insert(a,b);
            }
        }

        //contain at least one punctuation char
        if(s.equals("AllPrintables")) {
            int cNum=0;
            for (char P : passwd.toString().toCharArray()) {
                for (char N : nums) {
                    if(String.valueOf(P).equals(String.valueOf(N))) {
                        cNum+=1;
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            if(cNum==0) {
                int a=rnd.nextInt(passwd.length());
                char b=nums[rnd.nextInt(nums.length)];

                passwd.deleteCharAt(passwd.length()-1);
                passwd.insert(a,b);
            }

            int cPunc=0;
            for (char P : passwd.toString().toCharArray()) {
                for (char N : others) {
                    if(String.valueOf(P).equals(String.valueOf(N))) {
                        cPunc+=1;
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            if(cPunc==0) {
                int a=rnd.nextInt(passwd.length());
                char b=others[rnd.nextInt(others.length)];

                passwd.deleteCharAt(passwd.length()-1);
                passwd.insert(a,b);
            }
        }

        return (passwd.toString());
    }
}
