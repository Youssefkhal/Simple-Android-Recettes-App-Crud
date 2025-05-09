package com.example.recettesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecetteActivity extends AppCompatActivity {

    EditText titreInput, descriptionInput;
    Button ajouterBtn, supprimerBtn, logoutBtn;  // Declare Log Out button
    ListView recetteListView;
    RecetteDAO recetteDAO;
    ArrayList<Recette> recettes;
    ArrayAdapter<String> adapter;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette);

        titreInput = findViewById(R.id.titreInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        ajouterBtn = findViewById(R.id.ajouterBtn);
        supprimerBtn = findViewById(R.id.supprimerBtn);
        logoutBtn = findViewById(R.id.logoutBtn);  // Initialize Log Out button
        recetteListView = findViewById(R.id.recetteListView);

        recetteDAO = new RecetteDAO(this);
        refreshList();

        // Action when "Add/Update" button is clicked
        ajouterBtn.setOnClickListener(v -> {
            String titre = titreInput.getText().toString();
            String description = descriptionInput.getText().toString();

            if (titre.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Remplir les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedIndex == -1) {
                // Add a new recipe
                recetteDAO.ajouterRecette(new Recette(titre, description));
                Toast.makeText(this, "Recette ajoutée", Toast.LENGTH_SHORT).show();
            } else {
                // Update an existing recipe
                Recette selected = recettes.get(selectedIndex);
                selected.setTitre(titre);
                selected.setDescription(description);
                recetteDAO.updateRecette(selected);
                Toast.makeText(this, "Recette modifiée", Toast.LENGTH_SHORT).show();
                selectedIndex = -1;
                ajouterBtn.setText("Ajouter Recette");
                supprimerBtn.setVisibility(View.GONE);  // Hide delete button after update
            }

            titreInput.setText("");
            descriptionInput.setText("");
            refreshList();
        });

        // Action when "Delete" button is clicked
        supprimerBtn.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                // Delete the selected recipe
                recetteDAO.supprimerRecette(recettes.get(selectedIndex).getId());
                Toast.makeText(this, "Recette supprimée", Toast.LENGTH_SHORT).show();
                selectedIndex = -1;
                supprimerBtn.setVisibility(View.GONE);  // Hide delete button after deletion
                ajouterBtn.setText("Ajouter Recette");
                refreshList();
            }
        });

        // Action when a recipe is selected (click on a list item)
        recetteListView.setOnItemClickListener((parent, view, position, id) -> {
            Recette r = recettes.get(position);
            titreInput.setText(r.getTitre());
            descriptionInput.setText(r.getDescription());
            selectedIndex = position;
            ajouterBtn.setText("Modifier Recette");
            supprimerBtn.setVisibility(View.VISIBLE);  // Show delete button when a recipe is selected
        });

        // Action when a recipe is long-clicked (for deleting directly)
        recetteListView.setOnItemLongClickListener((parent, view, position, id) -> {
            recetteDAO.supprimerRecette(recettes.get(position).getId());
            Toast.makeText(this, "Recette supprimée", Toast.LENGTH_SHORT).show();
            refreshList();
            return true;
        });

        // Action when the "Log Out" button is clicked
        logoutBtn.setOnClickListener(v -> {
            // Log out the user by clearing any session data (if you have any)
            // Navigate to the Login screen
            Intent intent = new Intent(RecetteActivity.this, LoginActivity.class);  // Make sure you have LoginActivity
            startActivity(intent);
            finish();  // Close this activity
        });
    }

    private void refreshList() {
        recettes = recetteDAO.getAllRecettes();

        // Check if the list is empty
        if (recettes == null || recettes.isEmpty()) {
            Toast.makeText(this, "Aucune recette trouvée", Toast.LENGTH_SHORT).show();
        }

        ArrayList<String> data = new ArrayList<>();
        for (Recette r : recettes) {
            String description = r.getDescription();

            // Afficher la première ligne (avant le premier "\n")
            String shortDescription = description.split("\n")[0] + "..."vm;

            // Si tu veux limiter à 20 caractères
            if (shortDescription.length() > 20) {
                shortDescription = shortDescription.substring(0, 20) + "...";  // Ajoute "..." si la description est longue
            }

            data.add(r.getTitre() + "\n" + shortDescription);
        }

        // Only set adapter if there are items
        if (!data.isEmpty()) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
            recetteListView.setAdapter(adapter);
        }
    }


}
