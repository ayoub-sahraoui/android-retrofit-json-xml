package ma.ensa.testretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ma.ensa.testretrofit2.api.CompteApiService;
import ma.ensa.testretrofit2.api.CompteJsonApiService;
import ma.ensa.testretrofit2.api.CompteXmlApiService;
import ma.ensa.testretrofit2.beans.Compte;
import ma.ensa.testretrofit2.beans.CompteList;
import ma.ensa.testretrofit2.beans.CompteResponse;
import ma.ensa.testretrofit2.classes.CompteAdapter;
import ma.ensa.testretrofit2.config.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class CompteActivity extends AppCompatActivity implements CompteAdapter.OnCompteClickListener {
    private static final String TAG = "CompteActivity";
    private static final int REQUEST_CODE_ADD_EDIT = 1;

    private RecyclerView recyclerView;
    private CompteAdapter adapter;
    private List<Compte> compteList = new ArrayList<>();
    private CompteJsonApiService jsonApiService;
    private CompteXmlApiService xmlApiService;
    private boolean useJson = true; // Toggle between JSON and XML

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        compteList = new ArrayList<>(); // Initialize list here
        setupRecyclerView();
        setupApiServices();
        setupButtons();
        loadComptes();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.compteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompteAdapter(compteList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupApiServices() {
        jsonApiService = RetrofitClient.getJsonApi();
        xmlApiService = RetrofitClient.getXmlApi();
    }

    private void setupButtons() {
        findViewById(R.id.addCompteButton).setOnClickListener(v ->
                openAddEditCompteActivity(null));

        findViewById(R.id.toggleFormatButton).setOnClickListener(v -> {
            useJson = !useJson;
            Toast.makeText(this,
                    "Switched to " + (useJson ? "JSON" : "XML"),
                    Toast.LENGTH_SHORT).show();
            loadComptes();
        });
    }

    private void loadComptes() {
        if (useJson) {
            loadComptesJson();
        } else {
            loadComptesXml();
        }
    }

    private void loadComptesJson() {
        jsonApiService.getAllComptes().enqueue(new Callback<CompteResponse>() {
            @Override
            public void onResponse(Call<CompteResponse> call, Response<CompteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Compte> comptes = response.body().getComptes();
                    if (comptes != null) {
                        updateCompteList(comptes);
                    } else {
                        handleError("No data received from server");
                    }
                } else {
                    handleError("Failed to load comptes (JSON)");
                }
            }

            @Override
            public void onFailure(Call<CompteResponse> call, Throwable t) {
                Log.e(TAG, "JSON API call failed", t);
                handleError("Error loading comptes (JSON): " + t.getMessage());
            }
        });
    }

    private void loadComptesXml() {
        xmlApiService.getAllComptes().enqueue(new Callback<CompteList>() {
            @Override
            public void onResponse(Call<CompteList> call, Response<CompteList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Compte> comptes = response.body().getComptes();
                    if (comptes != null) {
                        updateCompteList(comptes);
                    } else {
                        handleError("No data received from server");
                    }
                } else {
                    handleError("Failed to load comptes (XML)");
                }
            }

            @Override
            public void onFailure(Call<CompteList> call, Throwable t) {
                Log.e(TAG, "XML API call failed", t);
                handleError("Error loading comptes (XML): " + t.getMessage());
            }
        });
    }

    private void updateCompteList(List<Compte> newComptes) {
        if (newComptes != null) {
            compteList.clear();
            compteList.addAll(newComptes);
            adapter.notifyDataSetChanged();
        }
    }

    private void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(Compte compte) {
        if (useJson) {
            deleteCompteJson(compte);
        } else {
            deleteCompteXml(compte);
        }
    }

    private void deleteCompteJson(Compte compte) {
        jsonApiService.deleteCompte(compte.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                handleDeleteResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                handleError("Error deleting compte (JSON)");
            }
        });
    }

    private void deleteCompteXml(Compte compte) {
        xmlApiService.deleteCompte(compte.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                handleDeleteResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                handleError("Error deleting compte (XML)");
            }
        });
    }

    private void handleDeleteResponse(boolean successful) {
        if (successful) {
            Toast.makeText(this, "Compte deleted successfully", Toast.LENGTH_SHORT).show();
            loadComptes();
        } else {
            handleError("Failed to delete compte");
        }
    }

    private final ActivityResultLauncher<Intent> addEditCompteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    loadComptes();
                }
            });

    private void openAddEditCompteActivity(@Nullable Compte compte) {
        Intent intent = new Intent(this, AddEditCompteActivity.class);
        if (compte != null) {
            intent.putExtra("compte", compte);
        }
        intent.putExtra("useJson", useJson);
        addEditCompteLauncher.launch(intent);
    }

    @Override
    public void onEdit(Compte compte) {
        openAddEditCompteActivity(compte);
    }
}