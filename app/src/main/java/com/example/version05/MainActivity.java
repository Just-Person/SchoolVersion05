package com.example.version05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.version05.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout root;
    public static final String EXTRA_TEXT = "com.example.version05.EXTRA_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister= findViewById(R.id.btnRegister);
        root = findViewById(R.id.root_element);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInWindow();
            }
        });

    }

    private void showSignInWindow() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Войти");
        dialog.setMessage("Введите данные для входа");
        LayoutInflater inflater = LayoutInflater.from(this);
        View sign_in_window = inflater.inflate(R.layout.sign_in_window,null);
        dialog.setView(sign_in_window);
        final MaterialEditText email = sign_in_window.findViewById(R.id.emailField);
        final MaterialEditText pass = sign_in_window.findViewById(R.id.passField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(email.getText().toString()))
                {
                    Snackbar.make(root, "Введите вашу почту",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (pass.getText().toString().length() < 5)
                {
                    Snackbar.make(root, "Введите пароль более чем 5 символов",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                startActivity(new Intent(MainActivity.this, Profile.class));

                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root,"Ошибка авторизации " + e.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

    private void showRegisterWindow() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрироваться");
        dialog.setMessage("Введите данные для регистрации");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View register_window = inflater.inflate(R.layout.register_window1,null);
        final View register_window2 = inflater.inflate(R.layout.register_window2,null);
        final View register_window3 = inflater.inflate(R.layout.register_window3,null);
        dialog.setView(register_window);
        final MaterialEditText email = register_window.findViewById(R.id.emailField);
        final MaterialEditText kl = register_window.findViewById(R.id.klField);
        final MaterialEditText pass = register_window.findViewById(R.id.passField);
        final MaterialEditText surname = register_window2.findViewById(R.id.surnameField);
        final MaterialEditText name = register_window2.findViewById(R.id.nameField);
        final MaterialEditText patronymic = register_window2.findViewById(R.id.patronymicField);
        final MaterialEditText letter = register_window3.findViewById(R.id.letterField);
        final MaterialEditText address = register_window3.findViewById(R.id.addressField);
        final MaterialEditText phone = register_window3.findViewById(R.id.phoneField);
        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Далее", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(email.getText().toString())) {
                            Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (Integer.parseInt(kl.getText().toString()) > 12 || Integer.parseInt(kl.getText().toString()) < 1) {
                            Snackbar.make(root, "Классы нумеруются от 1 до 11!", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (pass.getText().toString().length() < 5) {
                            Snackbar.make(root, "Введите пароль более чем 5 символов", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        dialog.setView(register_window2);
                        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        dialog.setPositiveButton("Далее", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (TextUtils.isEmpty(surname.getText().toString())) {
                                    Snackbar.make(root, "Введите вашу фамилию", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(name.getText().toString())) {
                                    Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(patronymic.getText().toString())) {
                                    Snackbar.make(root, "Введите ваше отчество", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                dialog.setView(register_window3);
                                dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (letter.getText().toString().length() > 1 || letter.getText().toString().length() < 1) {
                                            Snackbar.make(root, "Должна быть всего одна буква класса", Snackbar.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (TextUtils.isEmpty(address.getText().toString())) {
                                            Snackbar.make(root, "Введите ваш адрес", Snackbar.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (TextUtils.isEmpty(phone.getText().toString())) {
                                            Snackbar.make(root, "Введите ваш телефон", Snackbar.LENGTH_SHORT).show();
                                            return;
                                        }
                                //Регистрация пользователя
                                auth.createUserWithEmailAndPassword(email.getText().toString(),
                                        pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        User user = new User();
                                        user.setEmail(email.getText().toString());
                                        user.setKl(kl.getText().toString());
                                        user.setPass(pass.getText().toString());
                                        user.setSurname(surname.getText().toString());
                                        user.setName(name.getText().toString());
                                        user.setPatronymic(patronymic.getText().toString());
                                        user.setLetter(letter.getText().toString());
                                        user.setAddress(address.getText().toString());
                                        user.setPhone(phone.getText().toString());
                                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Snackbar.make(root, "Пользователь добавлен!", Snackbar.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(root, "Пользователь с таким email уже существует!" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });
                        dialog.show();
                    }
                });
                dialog.show();
                    }
        });
        dialog.show();
            }

}