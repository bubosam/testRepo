package com.example.android.mymoneymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;

    private String correct_username;
    private String correct_password;
    private String string_password;
    private String string_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correct_username = getString(R.string.correct_username);
        correct_password = getString(R.string.correct_password);
        username = (EditText) findViewById(R.id.login_user_name);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_button);

        username.setSingleLine();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(username)) {
                    string_username = username.getText().toString();

                    if (!isEmpty(password)) {
                        string_password = password.getText().toString();

                        if (string_username.equals(correct_username)) {
                            if (string_password.equals(correct_password)) {
                                Intent main_intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(main_intent);
                            } else {
                                openDialogInvalidUsernameOrPassword();
                            }
                        } else {
                            openDialogInvalidUsernameOrPassword();
                        }
                    } else {
                        openDialogPassword();
                    }


                } else {
                    openDialogUsername();
                }


            }
        });


    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }


    public void openDialogUsername() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle(getString(R.string.dialog_username_title));
        dialog.setMessage(getString(R.string.dialog_username_message));
        dialog.setPositiveButton(getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // returns to Login screen
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    public void openDialogPassword() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle(getString(R.string.dialog_password_title));
        dialog.setMessage(getString(R.string.dialog_password_message));
        dialog.setPositiveButton(getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // returns to Login screen
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    public void openDialogInvalidUsernameOrPassword() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle(getString(R.string.dialog_invalid_username_error));
        dialog.setMessage(getString(R.string.dialog_invalid_username_message));
        dialog.setPositiveButton(getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // returns to Login screen
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }


}
