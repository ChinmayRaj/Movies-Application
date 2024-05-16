package com.example.movieapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapplication.Database.Database;
import com.example.movieapplication.R;

public class RegisterActivity extends AppCompatActivity {
private TextView login;
private Button regbtn;
private EditText regname,email,pass,confpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        login=findViewById(R.id.regpageloginbtn);
        regname=findViewById(R.id.regname);
        email=findViewById(R.id.regemail);
        pass=findViewById(R.id.regpass);
        confpass=findViewById(R.id.confpass);
        regbtn=findViewById(R.id.regbtn);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = regname.getText().toString();
                String ema = email.getText().toString();
                String password = pass.getText().toString();
                String confp = confpass.getText().toString();
                Database db=new Database(getApplicationContext(),"User",null,1);
                if (username.length() == 0 || password.length() == 0 || ema.length() == 0 || confp.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    Log.d("Registration", "Registration Unsuccessful");
                } else {
                    if (password.compareTo(confp) == 0) {
                        if (is_Valid_Password(password)) {
                            db.register(username, ema, password);
                            Toast.makeText(RegisterActivity.this, "Registration Successful !! ", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(RegisterActivity.this, LoginActivity.class);
                            in.putExtra("email",ema);
                            startActivity(in);
                        } else {
                            Toast.makeText(RegisterActivity.this, "password must have at least eight characters .\n" + "password consists of only letters and digits.\n" +
                                    " password must contain at least two digits.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }


        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static boolean is_Valid_Password(String password) {

        if (password.length() < 8) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }


        return (charCount >= 2 && numCount >= 2);
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }
}