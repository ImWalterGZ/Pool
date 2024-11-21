package com.diseno.pool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private List<Chisme> chismes;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tipo;
        public TextView titulo;
        public TextView contenido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo = itemView.findViewById(R.id.categoriaTextView);
            titulo = itemView.findViewById(R.id.tituloTextView);
            contenido = itemView.findViewById(R.id.contenidoTextView);
        }
    }

    public Adaptador(List<Chisme> chismes) {
        this.chismes = chismes;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        Chisme chisme = chismes.get(position);
        holder.tipo.setText(chisme.getTipo());
        holder.titulo.setText(chisme.getTitulo());
        holder.contenido.setText(chisme.getContenido());
    }

    @Override
    public int getItemCount() {
        return chismes.size();
    }
}
