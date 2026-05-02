package projet.fst.ma.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import projet.fst.ma.app.classes.Etudiant;
import projet.fst.ma.app.service.EtudiantService;

public class MainActivity extends AppCompatActivity {

    private EditText nom;
    private EditText prenom;
    private Button add;

    private EditText id;
    private Button rechercher;
    private Button supprimer;
    private TextView res;

    // Méthode pour vider les champs après l’ajout
    void clear() {
        nom.setText("");
        prenom.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(projet.fst.ma.lab15.R.layout.activity_main);

        final EtudiantService es = new EtudiantService(this);

        nom = (EditText) findViewById(projet.fst.ma.lab15.R.id.nom);
        prenom = (EditText) findViewById(projet.fst.ma.lab15.R.id.prenom);
        add = (Button) findViewById(projet.fst.ma.lab15.R.id.bn);

        id = (EditText) findViewById(projet.fst.ma.lab15.R.id.id);
        rechercher = (Button) findViewById(projet.fst.ma.lab15.R.id.load);
        supprimer = (Button) findViewById(projet.fst.ma.lab15.R.id.delete);
        res = (TextView) findViewById(projet.fst.ma.lab15.R.id.res);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                es.create(new Etudiant(nom.getText().toString(), prenom.getText().toString()));
                clear();

                for (Etudiant e : es.findAll()) {
                    Log.d(e.getId() + "", e.getNom() + " " + e.getPrenom());
                }

                Toast.makeText(MainActivity.this, "Étudiant ajouté", Toast.LENGTH_SHORT).show();
            }
        });

        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    res.setText("");
                    Toast.makeText(MainActivity.this, "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }

                Etudiant e = es.findById(Integer.parseInt(txt));
                if (e == null) {
                    res.setText("");
                    Toast.makeText(MainActivity.this, "Étudiant introuvable", Toast.LENGTH_SHORT).show();
                    return;
                }

                res.setText(e.getNom() + " " + e.getPrenom());
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }

                Etudiant e = es.findById(Integer.parseInt(txt));
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Aucun étudiant à supprimer", Toast.LENGTH_SHORT).show();
                    return;
                }

                es.delete(e);
                res.setText("");
                Toast.makeText(MainActivity.this, "Étudiant supprimé", Toast.LENGTH_SHORT).show();
            }
        });
    }
}