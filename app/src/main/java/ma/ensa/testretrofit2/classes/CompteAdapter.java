package ma.ensa.testretrofit2.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import ma.ensa.testretrofit2.R;
import ma.ensa.testretrofit2.beans.Compte;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.CompteViewHolder> {

    private List<Compte> compteList;
    private OnCompteClickListener listener;

    public CompteAdapter(List<Compte> compteList, OnCompteClickListener listener) {
        this.compteList = compteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CompteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compte, parent, false);
        return new CompteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompteViewHolder holder, int position) {
        Compte compte = compteList.get(position);
        holder.bind(compte);
    }

    @Override
    public int getItemCount() {
        return compteList.size();
    }

    public interface OnCompteClickListener {
        void onEdit(Compte compte);
        void onDelete(Compte compte);
    }

    class CompteViewHolder extends RecyclerView.ViewHolder {

        TextView soldeTextView;
        TextView typeTextView;

        CompteViewHolder(@NonNull View itemView) {
            super(itemView);
            soldeTextView = itemView.findViewById(R.id.soldeTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);

            itemView.findViewById(R.id.editButton).setOnClickListener(view -> listener.onEdit(compteList.get(getAdapterPosition())));
            itemView.findViewById(R.id.deleteButton).setOnClickListener(view -> listener.onDelete(compteList.get(getAdapterPosition())));
        }

        void bind(Compte compte) {
            soldeTextView.setText("Solde: " + compte.getSolde());
            typeTextView.setText("Type: " + compte.getType().name());
        }
    }
}