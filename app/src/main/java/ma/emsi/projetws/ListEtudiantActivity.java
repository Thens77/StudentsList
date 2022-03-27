package ma.emsi.projetws;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import ma.emsi.projetws.adapter.EtudiantAdapter;
import ma.emsi.projetws.beans.Etudiant;

public class ListEtudiantActivity extends AppCompatActivity {
    private RecyclerView recycle;
    private FloatingActionButton addStudent;
    RequestQueue requestQueue;
    String loadUrl = "http://192.168.137.1/PhpProject1/ws/loadEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recycle = findViewById(R.id.listStudent);
        addStudent = findViewById(R.id.addStdent);

        addStudent.setOnClickListener(v -> startActivity(new Intent(ListEtudiantActivity.this, AddEtudiant.class)));

        loadData();
    }

    public void loadData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                loadUrl, response -> {
                    Log.d("TAG", response);
                    Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                    Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                    recycle.setAdapter(new EtudiantAdapter(ListEtudiantActivity.this, (List<Etudiant>) etudiants));
                    recycle.setLayoutManager(new LinearLayoutManager(ListEtudiantActivity.this));
                }, error -> {
                });
        requestQueue.add(request);
    }

}
