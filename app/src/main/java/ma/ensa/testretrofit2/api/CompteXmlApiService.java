package ma.ensa.testretrofit2.api;

import ma.ensa.testretrofit2.beans.Compte;
import ma.ensa.testretrofit2.beans.CompteList;
import retrofit2.Call;
import retrofit2.http.*;

public interface CompteXmlApiService {
    @Headers({
            "Accept: application/xml",
            "Content-Type: application/xml"
    })
    @POST("banque/comptes")
    Call<Compte> createCompte(@Body Compte compte);

    @Headers("Accept: application/xml")
    @GET("banque/comptes")
    Call<CompteList> getAllComptes();

    @Headers("Accept: application/xml")
    @GET("banque/comptes/{id}")
    Call<Compte> getCompteById(@Path("id") Long id);

    @Headers({
            "Accept: application/xml",
            "Content-Type: application/xml"
    })
    @PUT("banque/comptes/{id}")
    Call<Compte> updateCompte(@Path("id") Long id, @Body Compte compte);

    @Headers("Accept: application/xml")
    @DELETE("banque/comptes/{id}")
    Call<Void> deleteCompte(@Path("id") Long id);
}