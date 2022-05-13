package com.example.fdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import com.example.fdelivery.Class.Common;
import com.example.fdelivery.Class.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AESCrypt {

    Button btnReg,btnEnter,btnAd,btnTest;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference conntection;
    RelativeLayout root,assortment;

    TextView zal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black_light));

        getWindow().setNavigationBarColor(getResources().getColor(R.color.black_light));

        root = findViewById(R.id.root_element);
        btnEnter = findViewById(R.id.btnEnter);
        btnReg = findViewById(R.id.btnReg);

       // auth = FirebaseAuth.getInstance(); // запуск авторизации в бд
        db = FirebaseDatabase.getInstance(); // подключение к бд
        conntection = db.getReference("Users"); // обращаемся к таблице

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnterWindow();
            }
        });
    }

    private void showEnterWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this); //создание всплывающего окна, отображение в этом классе
        dialog.setTitle("Войти");
        dialog.setMessage("Введите данные для входа");

        LayoutInflater inflater = LayoutInflater.from(this);
        View enter_window = inflater.inflate(R.layout.activity_signin, null); // получаю шаблон inflater помещаю в reg_layout
        dialog.setView(enter_window); // уставналиваю полученный шаблон для всплывающего окна

        final MaterialEditText phone = enter_window.findViewById(R.id.emailField1);
        final MaterialEditText pass = enter_window.findViewById(R.id.passField1);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss(); //шаблон скрыватаеся при нажатии кнпоки
            }
        });

        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                conntection.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(phone.getText().toString()).exists()){
                            User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);
                            try
                            {
                                String l = AESCrypt.encrypt(pass.getText().toString());
                                if(user.getPassword().equals(l))
                                {
                                    Snackbar.make(root, "Успешный вход",Snackbar.LENGTH_SHORT).show();
                                    Intent homeIntent = new Intent(MainActivity.this,Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                }
                                else
                                {
                                    Snackbar.make(root, "Ошибка",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            catch(Exception ex){ System.out.println(ex.getMessage()); }
                        }
                        else
                        {
                            Snackbar.make(root, "Не найден",Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        dialog.show();
    }

    private void showRegisterWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this); //создание всплывающего окна, отображение в этом классе
        dialog.setTitle("Зарегестрироваться");//заголовок
        dialog.setMessage("Введите данные для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.activity_reg, null); // получаю шаблон inflater помещаю в reg_layout
        dialog.setView(reg_layout); // уставналиваю полученный шаблон для всплывающего окна


        final MaterialEditText pass = reg_layout.findViewById(R.id.passField);
        final MaterialEditText name = reg_layout.findViewById(R.id.nameField);
        final MaterialEditText phone = reg_layout.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss(); //шаблон скрыватаеся при нажатии кнпоки
            }
        });

        dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Вы не ввели имя",Snackbar.LENGTH_SHORT).show();//создает всплывающее окно
                    return;
                }

                if(TextUtils.isEmpty(phone.getText().toString())){
                    Snackbar.make(root, "Вы не ввели телефон",Snackbar.LENGTH_SHORT).show();//создает всплывающее окно
                    return;
                }

                if(pass.getText().toString().length() < 0){
                    Snackbar.make(root, "Введите пароль больше 6 символов",Snackbar.LENGTH_SHORT).show();//создает всплывающее окно
                    return;
                }

                conntection.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(phone.getText().toString()).exists())
                        {
                            Snackbar.make(root, "Успешная регистрация",Snackbar.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try
                            {
                                String l = AESCrypt.encrypt(pass.getText().toString());
                                User user = new User(name.getText().toString(),l,phone.getText().toString());
                                conntection.child(phone.getText().toString()).setValue(user);
                                Snackbar.make(root, "Успешная регистрация",Snackbar.LENGTH_SHORT).show();
                            }
                            catch(Exception ex){
                                System.out.println(ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        dialog.show();
    }
}

