package com.liontail.arfind.fragments.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liontail.arfind.R;
import com.liontail.arfind.dispositivos.DispositivoDto;

import java.util.List;

public class DispositivoCompartidosAdapter extends RecyclerView.Adapter<DispositivoCompartidosAdapter.ViewHolder> {

    private Context context;
    private List<DispositivoDto> dispositivoList;

    public DispositivoCompartidosAdapter(Context context, List<DispositivoDto> dispositivoList) {
        this.context = context;
        this.dispositivoList = dispositivoList;
    }
    @NonNull
    @Override
    public DispositivoCompartidosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_dispositivo_compartido, parent, false);
        return new DispositivoCompartidosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DispositivoCompartidosAdapter.ViewHolder holder, int position) {
        DispositivoDto dispositivo = dispositivoList.get(position);
        holder.txtTitulo.setText(dispositivo.getApodo());
    }

    @Override
    public int getItemCount() {
        return dispositivoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtSubtitulo;
        ImageView imgItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.dispositivo_nombre);
            txtSubtitulo = itemView.findViewById(R.id.dispositivo_descripcion_compartido);
            imgItem = itemView.findViewById(R.id.img_item_plan);
        }
    }
}
