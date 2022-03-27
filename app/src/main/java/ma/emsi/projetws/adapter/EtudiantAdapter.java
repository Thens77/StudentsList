package ma.emsi.projetws.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView ;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.emsi.projetws.EditActivity;
import ma.emsi.projetws.R;
import ma.emsi.projetws.beans.Etudiant;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {

    private List<Etudiant> etudiants;
    private LayoutInflater inflater;
    private Context context;
    RequestQueue requestQueue;
    String deleteUrl = "http://192.168.137.1/PhpProject1/ws/deleteEtudiant.php";

    public EtudiantAdapter(Context context, List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item, parent, false);
        return new EtudiantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder holder, int i) {

        holder.idf.setText(etudiants.get(i).getId()+"");
        holder.nom.setText(etudiants.get(i).getNom());
        holder.prenom.setText(etudiants.get(i).getPrenom());
        holder.ville.setText(etudiants.get(i).getVille());
        holder.sexe.setText(etudiants.get(i).getSexe());
        if(etudiants.get(i).getPhoto() == null) {
            String link = "drawable-v24/maleuser.png";
            Glide
                    .with(context)
                    .load(Uri.parse(link))
                    .apply(RequestOptions.fitCenterTransform())
                    .into(holder.image);
        }else {
            byte[] decodedString = Base64.decode(etudiants.get(i).getPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Glide
                    .with(context)
                    .load(decodedByte)
                    .apply(RequestOptions.fitCenterTransform())
                    .into(holder.image);

        }
        holder.itemView.setOnClickListener(v -> {
            int i1 = holder.getBindingAdapterPosition();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Choisir une option !");

            alertDialogBuilder.setPositiveButton("Supprimer", (dialog, which) -> {
                requestQueue = Volley.newRequestQueue(context);
                StringRequest request = new StringRequest(Request.Method.POST,
                        deleteUrl, response -> {
                            Log.d("TAG", response);
                            etudiants.remove(i1);
                            notifyItemRemoved(i1);
                            Toast.makeText(context, "Suppression avec succès", Toast.LENGTH_SHORT).show();
                        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", holder.idf.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(request);
            });
            alertDialogBuilder.setNegativeButton("Modifier", (dialog, which) -> {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("id", holder.idf.getText().toString());
                context.startActivity(intent);
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });


    }


    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public class EtudiantViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView nom, prenom, ville, sexe, idf;
        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            ville = itemView.findViewById(R.id.ville);
            sexe = itemView.findViewById(R.id.sexe);
            idf = itemView.findViewById(R.id.idf);
        }
    }
}
