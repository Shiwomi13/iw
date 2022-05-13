package com.example.fdelivery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;

import com.andremion.counterfab.CounterFab;
import com.example.fdelivery.Class.Common;
import com.example.fdelivery.korzina.Database;
import com.example.fdelivery.korzina.KorzinaActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fdelivery.Class.Category;
import com.example.fdelivery.Class.ItemClickListener;
import com.example.fdelivery.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    TextView txtUserName1;
    CounterFab fab;

    FirebaseDatabase db;
    DatabaseReference connect;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawer_layout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
        toolbar.setBackgroundColor(getResources().getColor(R.color.sky));
        setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance(); // подключение к бд
        connect = FirebaseDatabase.getInstance().getReference().child("Category");
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_m);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, KorzinaActivity.class));
                finish();
            }
        });
        fab.setCount(new Database(this).getCountCart());

        drawer_layout = findViewById(R.id.drawer_layout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //установки имени пользователя в nav panel
        View headerView = navigationView.getHeaderView(0);
        txtUserName1 = (TextView) headerView.findViewById(R.id.txtUserName);
        txtUserName1.setText(Common.currentUser.getName());

        loadMenu();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_menu)
        {
            startActivity(new Intent(Home.this, Home.class));
            finish();
        }
        else if (id == R.id.nav_cart)
        {
            startActivity(new Intent(Home.this, KorzinaActivity.class));
            finish();
        }
        else if (id == R.id.nav_orders)
        {
            startActivity(new Intent(Home.this, OrderActivity.class));
            finish();
        }
        else if (id == R.id.nav_log_out){
            Intent enter = new Intent(Home.this,MainActivity.class);
            enter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(enter);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void loadMenu() {

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(connect,Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder viewHolder, int position, @NonNull Category model) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.imageView);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodList = new Intent(Home.this,FoodActivity.class);
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_category,parent,false);
                MenuViewHolder viewHolder = new MenuViewHolder(view);
                return viewHolder;
            }
        };
        recycler_menu.setAdapter(adapter);
        adapter.startListening();
    }
}