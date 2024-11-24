package ma.ensa.testretrofit2.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompteResponse {
    @SerializedName("compte")
    private List<Compte> comptes;

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}