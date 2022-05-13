package com.example.fdelivery;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fdelivery.Class.Common;
import com.example.fdelivery.Class.ItemClickListener;
import com.example.fdelivery.Class.Order;
import com.example.fdelivery.Class.OrderFirebase;
import com.example.fdelivery.Class.Products;
import com.example.fdelivery.ViewHolder.FoodViewHolder;
import com.example.fdelivery.ViewHolder.OrderViewHolder;
import com.example.fdelivery.ViewHolder.OrderViewHolderAdmin;
import com.example.fdelivery.korzina.CorzinaAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbarForOrders;
    RelativeLayout root;

    FirebaseDatabase database;
    DatabaseReference requests;
    FirebaseRecyclerAdapter<OrderFirebase, OrderViewHolder> adapter;
    FirebaseRecyclerAdapter<OrderFirebase, OrderViewHolderAdmin> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        root = findViewById(R.id.root_element);
        toolbarForOrders = (Toolbar) findViewById(R.id.toolbarForOrders);
        setSupportActionBar(toolbarForOrders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarForOrders.setTitle("Заказы");
        toolbarForOrders.setBackgroundColor(getResources().getColor(R.color.sky));
        setSupportActionBar(toolbarForOrders);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference().child("Orders");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(Common.currentUser.getPhone().equals("0001")){
            loadOrders1();
        }else{
            loadOrders(Common.currentUser.getPhone());
        }


        toolbarForOrders.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderActivity.this, Home.class));
                finish();
            }
        });
    }

    private void loadOrders(String phone) {

        FirebaseRecyclerOptions<OrderFirebase> options = new FirebaseRecyclerOptions.Builder<OrderFirebase>()
                .setQuery(requests.orderByChild("phone").equalTo(phone),OrderFirebase.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<OrderFirebase, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull OrderFirebase orderFirebase) {
                orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtOrderStatus.setText(Common.convertCodeStatus(orderFirebase.getStatus()));
                orderViewHolder.txtOrderAdress.setText(orderFirebase.getAdress());
                orderViewHolder.txtOrderPhone.setText(orderFirebase.getPhone());

                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Snackbar.make(root,"Ваш заказ", Snackbar.LENGTH_SHORT).show();
                    }
                });

                orderViewHolder.btn_delete_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(adapter.getItem(i).getStatus().equals("0")){
                            deleteOrder(adapter.getRef(i).getKey());
                        }else {Snackbar.make(root,"Вы не можите удалить заказ", Snackbar.LENGTH_SHORT).show();}
                    }
                });

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_order_status,parent,false);
                OrderViewHolder viewHolder = new OrderViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void loadOrders1() {

        FirebaseRecyclerOptions<OrderFirebase> options = new FirebaseRecyclerOptions.Builder<OrderFirebase>()
                .setQuery(requests,OrderFirebase.class)
                .build();

        adapter1 = new FirebaseRecyclerAdapter<OrderFirebase, OrderViewHolderAdmin>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolderAdmin orderViewHolderAdmin, int i, @NonNull OrderFirebase orderFirebase) {
                orderViewHolderAdmin.txtOrderId.setText(adapter1.getRef(i).getKey());
                orderViewHolderAdmin.txtOrderStatus.setText(Common.convertCodeStatus(orderFirebase.getStatus()));
                orderViewHolderAdmin.txtOrderAdress.setText(orderFirebase.getAdress());
                orderViewHolderAdmin.txtOrderPhone.setText(orderFirebase.getPhone());

                orderViewHolderAdmin.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Snackbar.make(root,"Ваш хуй", Snackbar.LENGTH_SHORT).show();
                    }
                });

                orderViewHolderAdmin.btn_delete_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(adapter.getItem(i).getStatus().equals("0")){
                            deleteOrder(adapter.getRef(i).getKey());
                        }else {Snackbar.make(root,"Вы не можите удалить заказ", Snackbar.LENGTH_SHORT).show();}
                    }
                });

            }

            @NonNull
            @Override
            public OrderViewHolderAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_order_status,parent,false);
                OrderViewHolderAdmin viewHolder = new OrderViewHolderAdmin(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter1);
        adapter1.startListening();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle().equals(Common.UPDATE)){
            Snackbar.make(root,"Вы не можите удалить заказ", Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }

    private void deleteOrder(String key) {
        requests.child(key)
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(OrderActivity.this, new StringBuilder("Заказ ").append(key).append(" удален"),Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OrderActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}