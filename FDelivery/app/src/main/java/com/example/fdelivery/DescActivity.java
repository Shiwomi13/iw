package com.example.fdelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andremion.counterfab.CounterFab;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.fdelivery.Class.Desc;
import com.example.fdelivery.Class.Order;
import com.example.fdelivery.Class.Products;
import com.example.fdelivery.korzina.Database;
import com.example.fdelivery.korzina.KorzinaActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DescActivity extends AppCompatActivity {

    TextView fname,fprice,fdesc,g,kal,jir,bel,ugl;
    ImageView fimg;
    ElegantNumberButton nbtn;
    String foodId="";
    Button addKorzina;
    CounterFab btnGoToCartFromDesc;
    Toolbar toolbarForDesc;

    FirebaseDatabase db;
    DatabaseReference connect,connect1;
    Products curProduct;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        db = FirebaseDatabase.getInstance(); // подключение к бд
        connect = FirebaseDatabase.getInstance().getReference().child("Food");

        toolbarForDesc = (Toolbar) findViewById(R.id.toolbarForDesc);
        setSupportActionBar(toolbarForDesc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarForDesc.setTitle("");
        toolbarForDesc.setBackgroundColor(getResources().getColor(R.color.sky));
        setSupportActionBar(toolbarForDesc);

        fname = findViewById(R.id.product_name1);
        fprice = findViewById(R.id.product_price1);
        fdesc = findViewById(R.id.product_description1);
        fimg = findViewById(R.id.product_image1);
        g = findViewById(R.id.g);
        kal = findViewById(R.id.kal);
        jir= findViewById(R.id.jir);
        bel= findViewById(R.id.bel);
        ugl= findViewById(R.id.ugl);
        nbtn = findViewById(R.id.n_btn);
        btnGoToCartFromDesc = findViewById(R.id.btnGoToCartFromDesc);
        btnGoToCartFromDesc.setCount(new Database(this).getCountCart());
        addKorzina= findViewById(R.id.btnAddKorzina);

        toolbarForDesc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DescActivity.this, FoodActivity.class));
                finish();
            }
        });

        if(getIntent()!=null){
            foodId = getIntent().getStringExtra("FoodId");
        }

        btnGoToCartFromDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DescActivity.this, KorzinaActivity.class)); //переход на следующуб активность
                finish();
            }
        });

        addKorzina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId, curProduct.getName(),nbtn.getNumber(),curProduct.getPrice()
                ));
                btnGoToCartFromDesc.setCount(new Database(DescActivity.this).getCountCart());
                Toast.makeText(DescActivity.this,"Добавлен в корзину",Toast.LENGTH_SHORT).show();
            }
        });

        getDescFood(foodId);
    }

    private void getDescFood(String foodId) {
        connect.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curProduct = dataSnapshot.getValue(Products.class);

                Picasso.get().load(curProduct.getImage()).into(fimg);
                fname.setText(curProduct.getName());
                fdesc.setText(curProduct.getDesc());
                fprice.setText(curProduct.getPrice());
                g.setText(curProduct.getGram());
                kal.setText(curProduct.getKal());
                jir.setText(curProduct.getJir());
                bel.setText(curProduct.getBel());
                ugl.setText(curProduct.getUgl());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}