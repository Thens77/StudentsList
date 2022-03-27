package ma.emsi.projetws;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ma.emsi.projetws.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SpalshActivity extends AppCompatActivity {
    private TextView titre ;
    private ImageView logo ;
    private LottieAnimationView anim  ;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        titre = findViewById(R.id.titre);
        titre.animate().alpha(1f).setDuration(2000);
        titre.animate().translationY(200f).setDuration(4000);


        Thread t = new Thread() {
            public void run(){
                try {
                    sleep(5000);
                    Intent intent = new Intent(SpalshActivity.this ,ListEtudiantActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }
    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
