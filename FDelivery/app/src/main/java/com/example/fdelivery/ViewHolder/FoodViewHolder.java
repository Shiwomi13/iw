package com.example.fdelivery.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fdelivery.Class.ItemClickListener;
import com.example.fdelivery.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView product_name,product_price,product_description;
    public ImageView product_image;
    public ImageButton buttonAddFood;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView){
        super(itemView);

        product_price = (TextView) itemView.findViewById(R.id.product_price);
        product_description = (TextView) itemView.findViewById(R.id.product_description);
        product_name = (TextView) itemView.findViewById(R.id.product_name);
        product_image = (ImageView) itemView.findViewById(R.id.product_image);
        buttonAddFood = (ImageButton) itemView.findViewById(R.id.buttonAddFood);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
