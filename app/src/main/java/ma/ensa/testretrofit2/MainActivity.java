package ma.ensa.testretrofit2;

import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ma.ensa.testretrofit2.beans.Student;
import ma.ensa.testretrofit2.config.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    EditText nom, prenom,naissance;
    private  String Tag="Student";
    private  String Tag2="Affichage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAdd=findViewById(R.id.btn);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        naissance=findViewById(R.id.naissane);

        btnAdd.setOnClickListener(v -> creatStudent());




    }

    private void creatStudent() {

        Student student = new Student();
        student.setNom(nom.getText().toString());
        student.setPrenom(prenom.getText().toString());
        student.setNaissance(naissance.getText().toString());

        Call<Student> call= RetrofitClient.getApi().createStuent(student);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    Log.d(Tag,response.toString());
                    getAllStudent();

                }

            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(MainActivity.this,"error"+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });



    }

    private  void getAllStudent(){
        Call<List<Student>> call= RetrofitClient.getApi().getAll();

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()){
                    List<Student> students=response.body();

                    for (Student s:students) {
                        Log.d(Tag2,s.toString());
                    }


                }

            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"error"+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}