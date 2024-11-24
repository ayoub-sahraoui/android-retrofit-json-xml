package ma.ensa.testretrofit2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import ma.ensa.testretrofit2.api.CompteJsonApiService;
import ma.ensa.testretrofit2.api.CompteXmlApiService;
import ma.ensa.testretrofit2.beans.Compte;
import ma.ensa.testretrofit2.beans.TypeCompte;
import ma.ensa.testretrofit2.config.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditCompteActivity extends AppCompatActivity {
    private static final String TAG = "AddEditCompteActivity";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private EditText soldeEditText;
    private EditText dateCreationEditText;
    private Spinner typeSpinner;
    private boolean isEditMode = false;
    private boolean useJson;
    private CompteJsonApiService jsonApiService;
    private CompteXmlApiService xmlApiService;
    private Compte existingCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_compte);

        initializeViews();
        setupApiServices();
        setupSpinner();
        handleIntent();
        setupButtons();
    }

    private void initializeViews() {
        soldeEditText = findViewById(R.id.soldeEditText);
        dateCreationEditText = findViewById(R.id.dateCreationEditText);
        typeSpinner = findViewById(R.id.typeSpinner);
    }

    private void setupApiServices() {
        useJson = getIntent().getBooleanExtra("useJson", true);
        jsonApiService = RetrofitClient.getJsonApi();
        xmlApiService = RetrofitClient.getXmlApi();
    }

    private void setupSpinner() {
        ArrayAdapter<TypeCompte> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TypeCompte.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private void handleIntent() {
        if (getIntent().hasExtra("compte")) {
            isEditMode = true;
            existingCompte = (Compte) getIntent().getSerializableExtra("compte");
            if (existingCompte != null) {
                populateFields();
            }
        }
    }

    private void setupButtons() {
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(view -> validateAndSaveCompte());
        cancelButton.setOnClickListener(view -> finish());
    }

    private void populateFields() {
        if (existingCompte != null) {
            soldeEditText.setText(String.valueOf(existingCompte.getSolde()));
            dateCreationEditText.setText(DATE_FORMAT.format(existingCompte.getDateCreation()));

            TypeCompte type = existingCompte.getType();
            if (type != null) {
                int position = ((ArrayAdapter<TypeCompte>) typeSpinner.getAdapter())
                        .getPosition(type);
                typeSpinner.setSelection(position);
            }
        }
    }

    private void validateAndSaveCompte() {
        try {
            double solde = Double.parseDouble(soldeEditText.getText().toString());
            Date dateCreation = DATE_FORMAT.parse(dateCreationEditText.getText().toString());
            TypeCompte type = (TypeCompte) typeSpinner.getSelectedItem();

            if (dateCreation == null || type == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Compte compte = new Compte();
            if (isEditMode && existingCompte != null) {
                compte.setId(existingCompte.getId());
            }
            compte.setSolde(solde);
            compte.setDateCreation(dateCreation);
            compte.setType(type);

            saveCompte(compte);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(this, "Please enter a valid date (YYYY-MM-DD)", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCompte(Compte compte) {
        if (useJson) {
            saveCompteJson(compte);
        } else {
            saveCompteXml(compte);
        }
    }

    private void saveCompteJson(Compte compte) {
        Call<Compte> call = isEditMode ?
                jsonApiService.updateCompte(compte.getId(), compte) :
                jsonApiService.createCompte(compte);

        call.enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                handleSaveResponse(response);
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                Log.e(TAG, "JSON API call failed", t);
                handleError("Error saving compte (JSON): " + t.getMessage());
            }
        });
    }

    private void saveCompteXml(Compte compte) {
        Call<Compte> call = isEditMode ?
                xmlApiService.updateCompte(compte.getId(), compte) :
                xmlApiService.createCompte(compte);

        call.enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                handleSaveResponse(response);
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                Log.e(TAG, "XML API call failed", t);
                handleError("Error saving compte (XML): " + t.getMessage());
            }
        });
    }

    private void handleSaveResponse(Response<Compte> response) {
        if (response.isSuccessful() && response.body() != null) {
            Toast.makeText(this,
                    isEditMode ? "Compte updated successfully" : "Compte created successfully",
                    Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            handleError("Failed to save compte. Server returned: " + response.code());
        }
    }

    private void handleError(String message) {
        Log.e(TAG, message);
        runOnUiThread(() ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        );
    }
}