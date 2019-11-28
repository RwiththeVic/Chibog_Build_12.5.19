package com.fcpc.chibogservices.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fcpc.chibogservices.R;
import com.fcpc.chibogservices.core.Globals;
import com.fcpc.chibogservices.objects.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends BaseAdapter {


    private List<Food> foodList;
    private Activity act;

    private List<Food> selectedFood;

    List<Boolean> activeStates;

    public FoodListAdapter(List<Food> flist, Activity ac){
        this.foodList = flist;
        this.act = ac;
        this.selectedFood = new ArrayList<>();
        this.activeStates = new ArrayList<>();

        for(int x=0;x<flist.size();x++){
            this.activeStates.add(false);
        }
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Food currFood = foodList.get(position);

        if(convertView == null){

            View v = this.act.getLayoutInflater().inflate(R.layout.list_layout_item,null);
            TextView pName = v.findViewById(R.id.tvFoodName);
            TextView pPrice = v.findViewById(R.id.tvProdPrice);
            ImageView img = v.findViewById(R.id.imgFoodIcon);
            CheckBox cb = v.findViewById(R.id.checkProd);

            cb.setChecked(activeStates.get(position));

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    activeStates.set(position,b);
                    if(b){
                        selectedFood.add(currFood);
                    }
                    else{
                        selectedFood.remove(currFood);
                    }

                    computeTotal();
                }
            });

            Picasso.get().load(Globals.IMAGE_URL + currFood.image).into(img);

            pName.setText(currFood.name);
            pPrice.setText(currFood.price + " PHP");
            return v;
        }
        TextView pName = convertView.findViewById(R.id.tvFoodName);
        TextView pPrice = convertView.findViewById(R.id.tvProdPrice);
        ImageView img = convertView.findViewById(R.id.imgFoodIcon);
        CheckBox cb = convertView.findViewById(R.id.checkProd);

        cb.setChecked(activeStates.get(position));

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                activeStates.set(position,b);
                if(b){
                    selectedFood.add(currFood);
                }
                else{
                    selectedFood.remove(currFood);
                }

                computeTotal();
            }
        });

        Picasso.get().load(Globals.IMAGE_URL + currFood.image).into(img);

        pName.setText(currFood.name);
        pPrice.setText(currFood.price + " PHP");
        return convertView;
    }

    private void computeTotal(){

        Double sum = 0d;
        for(int i=0;i<selectedFood.size();i++){
            sum += Double.parseDouble(selectedFood.get(i).price);
        }

        Toast.makeText(act, "Total price: " + sum.toString() + " PHP", Toast.LENGTH_SHORT).show();
    }
}
