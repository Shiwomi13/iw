package com.example.fdelivery.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fdelivery.Class.Common;
import com.example.fdelivery.Class.ItemClickListener;
import com.example.fdelivery.R;

public class OrderViewHolderAdmin extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener  {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAdress;
    public ImageButton btn_delete_order;

    private ItemClickListener itemClickListener;

    public OrderViewHolderAdmin(View itemView){
        super(itemView);

        txtOrderId = (TextView) itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.order_phone);
        txtOrderAdress = (TextView) itemView.findViewById(R.id.order_adress);
        btn_delete_order = (ImageButton) itemView.findViewById(R.id.btn_delete_order);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Выберите статус:");
        contextMenu.add(0,0,getAdapterPosition(), "Обработка");
        contextMenu.add(0,1,getAdapterPosition(), "В пути");
        contextMenu.add(0,2,getAdapterPosition(), "Доставлен");
    }
}

