package com.liontail.arfind.notificaciones;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.liontail.arfind.R;

import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder> {

    private List<NotificacionDto> notificaciones;

    // Constructor vacío
    public NotificacionAdapter(List<NotificacionDto> notificaciones) {
        this.notificaciones = notificaciones;
    }

    @Override
    public NotificacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pedido, parent, false);
        return new NotificacionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(NotificacionViewHolder holder, int position) {
        if (notificaciones != null && notificaciones.size() > 0) {
            NotificacionDto notificacion = notificaciones.get(position);
            holder.mensajeNotificacion.setText(notificacion.getMensaje());
            holder.fechaNotificacion.setText( "A las : " + notificacion.getFecha_envio().toDate().toString());
        }
    }

    @Override
    public int getItemCount() {
        return notificaciones != null ? notificaciones.size() : 0;
    }

    // Método para actualizar las notificaciones
    @SuppressLint("NotifyDataSetChanged")
    public void actualizarNotificaciones(List<NotificacionDto> notificaciones) {
        this.notificaciones = notificaciones;
        notifyDataSetChanged();  // Notificamos que los datos han cambiado
    }

    public static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        TextView mensajeNotificacion;
        TextView fechaNotificacion;

        public NotificacionViewHolder(View itemView) {
            super(itemView);
            mensajeNotificacion = itemView.findViewById(R.id.mensajeNotificacion);
            fechaNotificacion = itemView.findViewById(R.id.fechaNotificacion);
        }
    }
}
