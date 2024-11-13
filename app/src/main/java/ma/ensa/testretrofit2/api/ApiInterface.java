package ma.ensa.testretrofit2.api;

import java.util.List;

import ma.ensa.testretrofit2.beans.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api")
    Call <Student>  createStuent(@Body Student student);
    @GET("api")
    Call<List<Student>> getAll();




}
