package ma.ensa.testretrofit2.api;

import ma.ensa.testretrofit2.beans.Compte;
import ma.ensa.testretrofit2.beans.CompteResponse;
import ma.ensa.testretrofit2.classes.SoapRequest;
import ma.ensa.testretrofit2.classes.SoapResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface CompteSoapApiService {
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: \"http://banque.services/createCompte\""
    })
    @POST("banque/ws/comptes")
    Call<SoapResponse<Compte>> createCompte(@Body SoapRequest<Compte> request);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: \"http://banque.services/getAllComptes\""
    })
    @POST("banque/ws/comptes")
    Call<SoapResponse<CompteResponse>> getAllComptes(@Body SoapRequest<Void> request);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: \"http://banque.services/getCompteById\""
    })
    @POST("banque/ws/comptes")
    Call<SoapResponse<Compte>> getCompteById(@Body SoapRequest<Long> request);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: \"http://banque.services/updateCompte\""
    })
    @POST("banque/ws/comptes")
    Call<SoapResponse<Compte>> updateCompte(@Body SoapRequest<Compte> request);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: \"http://banque.services/deleteCompte\""
    })
    @POST("banque/ws/comptes")
    Call<SoapResponse<Void>> deleteCompte(@Body SoapRequest<Long> request);
}