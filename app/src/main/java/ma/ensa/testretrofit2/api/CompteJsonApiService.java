package ma.ensa.testretrofit2.api;

import ma.ensa.testretrofit2.beans.Compte;
import ma.ensa.testretrofit2.beans.CompteResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface CompteJsonApiService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("banque/comptes")
    Call<Compte> createCompte(@Body Compte compte);

    @Headers("Accept: application/json")
    @GET("banque/comptes")
    Call<CompteResponse> getAllComptes();

    @Headers("Accept: application/json")
    @GET("banque/comptes/{id}")
    Call<Compte> getCompteById(@Path("id") Long id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("banque/comptes/{id}")
    Call<Compte> updateCompte(@Path("id") Long id, @Body Compte compte);

    @Headers("Accept: application/json")
    @DELETE("banque/comptes/{id}")
    Call<Void> deleteCompte(@Path("id") Long id);
}