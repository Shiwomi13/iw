package com.example.fdelivery.korzina;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fdelivery.Class.Common;
import com.example.fdelivery.Class.Order;
import com.example.fdelivery.Class.OrderFirebase;
import com.example.fdelivery.Class.User;
import com.example.fdelivery.FoodActivity;
import com.example.fdelivery.Home;
import com.example.fdelivery.OrderActivity;
import com.example.fdelivery.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class KorzinaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout root;

    FirebaseDatabase db;
    DatabaseReference connect,connect1;

    TextView txtTotalPrice;
    Button btnPlace;
    ImageButton btnClearCart;
    Toolbar toolbarForKorzina;

    CorzinaAdapter adapter;
    List<Order> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korzina);

        root = findViewById(R.id.id_activity_korzina);

        toolbarForKorzina = (Toolbar) findViewById(R.id.toolbarForKorzina);
        setSupportActionBar(toolbarForKorzina);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarForKorzina.setTitle("Корзина");
        toolbarForKorzina.setBackgroundColor(getResources().getColor(R.color.sky));
        setSupportActionBar(toolbarForKorzina);

        db = FirebaseDatabase.getInstance(); // подключение к бд
        connect = FirebaseDatabase.getInstance().getReference().child("Category");
        connect1 = FirebaseDatabase.getInstance().getReference().child("Orders");

        recyclerView = (RecyclerView) findViewById(R.id.listKorzina);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        btnClearCart = findViewById(R.id.btnClearCart);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.size() > 0){
                    ShowAlerDialog();
                }else{
                    Snackbar.make(root, "Корзина пуста",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllCart();
            }
        });

        toolbarForKorzina.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KorzinaActivity.this, Home.class));
                finish();
            }
        });

        loadListKorzina();
    }

    private void ShowAlerDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(KorzinaActivity.this);
        alertDialog.setTitle("⠀⠀⠀");
        alertDialog.setMessage("Введите ваш адрес: ");

        final EditText edtAdress = new EditText(KorzinaActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAdress.setLayoutParams(lp);
        alertDialog.setView(edtAdress);
        alertDialog.setIcon(R.drawable.ic_menu);

        OrderFirebase orderFirebase = new OrderFirebase();

        alertDialog.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderFirebase orderFirebase = new OrderFirebase(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAdress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        Common.currentUser.getName(),
                        cart
                );

                connect1.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(orderFirebase);

                new Database(getBaseContext()).clearCart();

                Snackbar.make(root,"Ваш заказ оформлен. Ожидайте",Snackbar.LENGTH_SHORT).show();
                loadListKorzina();
            }
        });

        alertDialog.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListKorzina() {
        cart = new Database(this).getCarts();
        adapter = new CorzinaAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for (Order order:cart){
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        }
        Locale locale = new Locale("ru","RU");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle().equals(Common.DELETE)){
            deleteCart(item.getOrder());
        }
        return true;
    }

    private void deleteCart(int position) {
        cart.remove(position);
        new Database(this).clearCart();
        for (Order item:cart)
            new Database(this).addToCart(item);
        loadListKorzina();
    }

    private void deleteAllCart() {
        new Database(this).clearCart();
        loadListKorzina();
    }
}
