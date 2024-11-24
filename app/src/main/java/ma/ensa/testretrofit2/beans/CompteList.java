package ma.ensa.testretrofit2.beans;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "comptes")
public class CompteList {
    @ElementList(inline = true, entry = "compte")
    private List<Compte> comptes;

    // Constructor
    public CompteList() {
    }

    // Getters and setters
    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}