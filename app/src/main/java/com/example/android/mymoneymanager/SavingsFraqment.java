package com.example.android.mymoneymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SavingsFraqment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.savings_fragment, container, false);

        setFlags();

        final EditText tr_card = (EditText) view.findViewById(R.id.tr_card_value);
        final EditText lunch = (EditText) view.findViewById(R.id.lunch_value);
        final EditText cafeteria = (EditText) view.findViewById(R.id.cafeteria_value);
        final EditText spent_money = (EditText) view.findViewById(R.id.spentMoney);

        ImageButton tr_card_butt = (ImageButton) view.findViewById(R.id.im_tr_card);
        ImageButton lunch_butt = (ImageButton) view.findViewById(R.id.im_lunch);
        ImageButton cafeteria_butt = (ImageButton) view.findViewById(R.id.im_cafeteria);

        Button subtract_lunch = (Button) view.findViewById(R.id.subtractLunch);
        Button subtract_shopping = (Button) view.findViewById(R.id.subtractShopping);


        final float tr_card_value = getFloatFromSharedPrefs("tr_card_float");
        final float cafeteriaValue = getFloatFromSharedPrefs("cafeteria_float");


        setTextToField(tr_card, "tr_card_float");
        setTextToField(lunch, "lunch_float");
        setTextToField(cafeteria, "cafeteria_float");


        tr_card_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldfEmpty(tr_card)) {
                    float tr_card_value = Float.valueOf(tr_card.getText().toString());
                    saveFloatToSharedPrefs("tr_card_float", tr_card_value);
                    showToastPositive();
                } else {
                    showToastNegative();
                }
            }
        });

        lunch_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldfEmpty(lunch)) {
                    if (isCafeteriaOrLunchEmpty(cafeteria)) {
                        saveFloatToSharedPrefs("cafeteria_float", 0);
                        setTextToField(cafeteria, "cafeteria_float");
                    } else {
                        float lunch_value = Float.valueOf(lunch.getText().toString());
                        float cafeteria_value2 = Float.valueOf(cafeteria.getText().toString());
                        saveFloatToSharedPrefs("lunch_float", lunch_value);
                        saveFloatToSharedPrefs("cafeteria_float", cafeteria_value2);
                        showToastPositive();
                    }
                } else {
                    showToastNegative();
                }
            }
        });

        cafeteria_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldfEmpty(cafeteria)) {
                    if (isCafeteriaOrLunchEmpty(lunch)) {
                        saveFloatToSharedPrefs("lunch_float", 0);
                        setTextToField(lunch, "lunch_float");

                    } else {
                        float cafeteria_value = Float.valueOf(cafeteria.getText().toString());
                        float lunch_value2 = Float.valueOf(lunch.getText().toString());
                        saveFloatToSharedPrefs("cafeteria_float", cafeteria_value);
                        saveFloatToSharedPrefs("lunch_float", lunch_value2);
                        showToastPositive();
                    }

                } else {
                    showToastNegative();
                }
            }
        });

        subtract_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldfEmpty(spent_money)) {
                    float spent_money_value = Float.valueOf(spent_money.getText().toString());
                    Log.d("spent mon", String.valueOf(spent_money_value));

                    float lunch_money_final_value = getFloatFromSharedPrefs("lunch_float") - spent_money_value;
                    Log.d("lunch mon final ", String.valueOf(lunch_money_final_value));

                    float tr_card_money_final = getFloatFromSharedPrefs("tr_card_float") - spent_money_value;
                    Log.d("tr card money final", String.valueOf(tr_card_money_final));


                    saveFloatToSharedPrefs("lunch_float", lunch_money_final_value);


                    saveFloatToSharedPrefs("tr_card_float", tr_card_money_final);

                    setTextToField(lunch, "lunch_float");
                    setTextToField(tr_card, "tr_card_float");
                    setTextToField(spent_money, "");
                    showToastPositive();
                } else {
                    showToastNegative();
                }
            }
        });

        subtract_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldfEmpty(spent_money)) {
                    float spent_money_value = Float.valueOf(spent_money.getText().toString());
                    float cafeteria_money_final_value = getFloatFromSharedPrefs("cafeteria_float") - spent_money_value;
                    float tr_card_money_final = getFloatFromSharedPrefs("tr_card_float") - spent_money_value;
                    saveFloatToSharedPrefs("cafeteria_float", cafeteria_money_final_value);
                    saveFloatToSharedPrefs("tr_card_float", tr_card_money_final);
                    setTextToField(cafeteria, "cafeteria_float");
                    setTextToField(tr_card, "tr_card_float");
                    setTextToField(spent_money, "");
                    showToastPositive();
                } else {
                    showToastNegative();
                }
            }
        });
        return view;

    }


    private void saveFloatToSharedPrefs(String key, float value) {
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("mmPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    private float getFloatFromSharedPrefs(String key) {
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("mmPrefs", Context.MODE_PRIVATE);
        return sharedPrefs.getFloat(key, 0.0f);
    }

    private void showToastPositive() {
        Toast.makeText(getActivity(), getString(R.string.value_saved_positive), Toast.LENGTH_SHORT).show();
    }

    private void showToastNegative() {
        Toast.makeText(getActivity(), getString(R.string.value_saved_negative), Toast.LENGTH_SHORT).show();
    }

    private boolean isFieldfEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0) {
            return false;
        }
        return true;
    }

    private boolean isCafeteriaOrLunchEmpty(EditText text) {
        if (text.getText().toString().trim().length() <= 0) {
            return true;
        }
        return false;
    }

    private void setTextToField(EditText field, String sharedPrefsKey) {
        field.setText(String.valueOf(getFloatFromSharedPrefs(sharedPrefsKey)));
    }

    private void setFlags() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private boolean isBiggerThanZero(Integer value) {
        if (value > 0)
            return true;
        return false;
    }
}
