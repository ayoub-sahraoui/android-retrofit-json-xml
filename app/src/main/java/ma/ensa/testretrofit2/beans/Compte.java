package ma.ensa.testretrofit2.beans;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


import java.io.Serializable;
import java.util.Date;


@Root(name = "compte")
public class Compte implements Serializable {

    @SerializedName("id")
    @Element(name = "id", required = false)
    private Long id;

    @SerializedName("solde")
    @Element(name = "solde")
    private double solde;

    @SerializedName("dateCreation")
    @Element(name = "dateCreation")
    private Date dateCreation;

    @SerializedName("type")
    @Element(name = "type")
    private TypeCompte type;

    // Default constructor
    public Compte() {}

    // Parameterized constructor
    public Compte(Long id, double solde, Date dateCreation, TypeCompte type) {
        this.id = id;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public TypeCompte getType() {
        return type;
    }

    public void setType(TypeCompte type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", solde=" + solde +
                ", dateCreation=" + dateCreation +
                ", type=" + type +
                '}';
    }
}
