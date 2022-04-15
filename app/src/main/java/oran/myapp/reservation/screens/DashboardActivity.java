package oran.myapp.reservation.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.ServicesAdapter;
import oran.myapp.reservation.modele.Service;

public class DashboardActivity extends AppCompatActivity implements ServicesAdapter.ServiceClickListener {

    private RecyclerView Srcv;
    private ArrayList<Service> SAL = new ArrayList<>();
    private ServicesAdapter SAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();

        LinearLayoutManager LM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        Srcv.setLayoutManager(LM);
        Srcv.setAdapter(SAdapter);

        SAL.add(new Service("Generalist",R.drawable.doctor));
        SAL.add(new Service("Dentist",R.drawable.doctor));
        SAL.add(new Service("opticien",R.drawable.doctor));
        SAL.add(new Service("tbib l galb ",R.drawable.doctor));

    }

    // Views Initialization
    private void init(){
        Srcv = findViewById(R.id.ServiesRecycler);
        SAdapter = new ServicesAdapter(this,SAL,this);
    }

    @Override
    public void OnServiceClick(int position) {

    }
}