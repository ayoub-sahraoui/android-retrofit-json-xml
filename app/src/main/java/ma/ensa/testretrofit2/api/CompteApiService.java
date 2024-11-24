package ma.ensa.testretrofit2.api;

import ma.ensa.testretrofit2.beans.Compte;
import ma.ensa.testretrofit2.beans.CompteList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CompteApiService {

    @POST("banque/comptes")
    Call<Compte> createCompte(@Body Compte compte);

    @GET("banque/comptes")
    Call<CompteList> getAllComptes();

    @GET("banque/comptes/{id}")
    Call<Compte> getCompteById(@Path("id") Long id);

    @PUT("banque/comptes/{id}")
    Call<Compte> updateCompte(@Path("id") Long id, @Body Compte compte);

    @DELETE("banque/comptes/{id}")
    Call<Void> deleteCompte(@Path("id") Long id);
}