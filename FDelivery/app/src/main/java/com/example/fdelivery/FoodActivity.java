package com.example.fdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andremion.counterfab.CounterFab;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.fdelivery.Class.ItemClickListener;
import com.example.fdelivery.Class.Order;
import com.example.fdelivery.Class.OrderFirebase;
import com.example.fdelivery.Class.Products;
import com.example.fdelivery.ViewHolder.FoodViewHolder;

import com.example.fdelivery.ViewHolder.OrderViewHolder;
import com.example.fdelivery.korzina.Database;
import com.example.fdelivery.korzina.KorzinaActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodActivity extends AppCompatActivity{
    FirebaseDatabase db;
    DatabaseReference connect;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Products, FoodViewHolder> adapter;

    String categoryId="";
    String foodId="";
    TextView textView;
    Products curProduct;

    Toolbar toolbarForFood;

    CounterFab fab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        db = FirebaseDatabase.getInstance(); // подключение к бд
        connect = FirebaseDatabase.getInstance().getReference("Food");

        toolbarForFood = (Toolbar) findViewById(R.id.toolbarForFood);
        setSupportActionBar(toolbarForFood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarForFood.setTitle("");
        toolbarForFood.setBackgroundColor(getResources().getColor(R.color.sky));
        setSupportActionBar(toolbarForFood);

        recycler_menu = findViewById(R.id.recycler_food);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        textView = findViewById(R.id.zalupa);
        fab1 = findViewById(R.id.fab1);
        fab1.setCount(new Database(this).getCountCart());

        if(getIntent()!=null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        toolbarForFood.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this, Home.class));
                finish();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this, KorzinaActivity.class));
                finish();
            }
        });

        loadListFood(categoryId);
    }


    public void loadListFood(String categoryId) {
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(connect.orderByChild("MenuId").equalTo(categoryId),Products.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Products, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder, int position, @NonNull Products model) {
                viewHolder.product_name.setText(model.getName());
                viewHolder.product_description.setText(model.getDesc());
                viewHolder.product_price.setText(model.getPrice());

                Picasso.get().load(model.getImage()).into(viewHolder.product_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDesc = new Intent(FoodActivity.this,DescActivity.class);
                        foodDesc.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDesc);
                    }
                });

                viewHolder.buttonAddFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Database(getBaseContext()).addToCart(new Order(
                                adapter.getRef(position).getKey(),
                                model.getName(),
                                "1",
                                model.getPrice()
                        ));
                        fab1.setCount(new Database(FoodActivity.this).getCountCart());
                    }
                });
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_food,parent,false);
                FoodViewHolder viewHolder = new FoodViewHolder(view);
                return viewHolder;
            }
        };
        Log.d("TAG", "Check category " +categoryId);
        recycler_menu.setAdapter(adapter);
        adapter.startListening();
    }
}
