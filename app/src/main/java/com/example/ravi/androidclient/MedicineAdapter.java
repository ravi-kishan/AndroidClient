package com.example.ravi.androidclient;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends ArrayAdapter<Medicine> {

    private Context mContext;
    private List<Medicine> medicinesList = new ArrayList<>();

    public MedicineAdapter(@NonNull Context context,  ArrayList<Medicine> list) {
        super(context, 0 , list);
        mContext = context;
        medicinesList = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.medicine_list_item_edit,parent,false);

        Medicine currentMedicine = medicinesList.get(position);


        TextView name = (TextView) listItem.findViewById(R.id.medicine_enter);
        name.setText(currentMedicine.medicine);

        TextView quantity = (TextView) listItem.findViewById(R.id.quantity_enter);
        quantity.setText(currentMedicine.quantity);

        return listItem;
    }
}
