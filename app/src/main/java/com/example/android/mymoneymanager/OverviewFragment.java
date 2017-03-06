package com.example.android.mymoneymanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by patrik.patinak on 2/10/2017.
 */

public class OverviewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_fragment, container, false);


        TextView monthView = (TextView) view.findViewById(R.id.month);
        TextView incomeView = (TextView) view.findViewById(R.id.income);
        TextView outcomeView = (TextView) view.findViewById(R.id.outcome);
        final TextView savingsView = (TextView) view.findViewById(R.id.savings);
        ImageButton addSavedMoney = (ImageButton) view.findViewById(R.id.addSavedMoney);
        PieGraph pg = (PieGraph) view.findViewById(R.id.graph);

        incomeView.setText(getString(R.string.income_price));
        outcomeView.setText(getString(R.string.outcome_price));
        savingsView.setText(String.valueOf(getIntFromSharedPrefs()));


        addSavedMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSavings(savingsView);
            }
        });

        createGraph(pg);

        int month = getDate();
        String monthToShow = parseDate(month);
        monthView.setText(monthToShow);
        return view;
    }


    private int getDate() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }

    private String parseDate(int month) {
        String monthFinal = "";

        HashMap<Integer, String> mapMonth = new HashMap<Integer, String>();
        mapMonth.put(1, "JANUARY");
        mapMonth.put(2, "FEBRUARY");
        mapMonth.put(3, "MARCH");
        mapMonth.put(4, "APRIL");
        mapMonth.put(5, "MAY");
        mapMonth.put(6, "JUNE");
        mapMonth.put(7, "JULY");
        mapMonth.put(8, "AUGUST");
        mapMonth.put(9, "SEPTEMBER");
        mapMonth.put(10, "OCTOBER");
        mapMonth.put(11, "NOVEMBER");
        mapMonth.put(12, "DECEMBER");
        monthFinal = mapMonth.get(month);

        return monthFinal;
    }

    private void createGraph(PieGraph pg) {
        PieSlice income = new PieSlice();
        income.setColor(Color.parseColor("#2884E0"));
        income.setValue((float) 4.85);


        PieSlice outcome = new PieSlice();
        outcome.setColor(Color.parseColor("#FA0505"));
        outcome.setValue((float) 5.15);


        ArrayList<PieSlice> arr = new ArrayList<>();
        arr.add(0, income);
        arr.add(1, outcome);
        pg.setSlices(arr);
        pg.setThickness(120);
    }

    private void onClickGraph(PieGraph pg) {
        pg.setOnSliceClickedListener(new PieGraph.OnSliceClickedListener() {
            @Override
            public void onClick(int index) {
                if (index == 0) {

                }
            }
        });
    }

    private void saveIntToSharedPrefs(int money_saved) {
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("mmPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("savings", money_saved);
        editor.commit();
    }

    private int getIntFromSharedPrefs() {
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("mmPrefs", Context.MODE_PRIVATE);
        int moneySaved = sharedPrefs.getInt("savings", 0);
        return moneySaved;
    }

    public void openDialogSavings(final TextView savingsView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
        dialog.setCancelable(false);
        dialog.setTitle(getString(R.string.savings_title));
        dialog.setMessage(getString(R.string.savings_message));
        dialog.setPositiveButton(getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int saved_old = Integer.parseInt(savingsView.getText().toString());
                int saved_new = saved_old + 400;
                saveIntToSharedPrefs(saved_new);
                savingsView.setText("" + saved_new);
            }
        }) .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }


}
