package com.liontail.arfind.fragments.adapater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liontail.arfind.R;
import com.liontail.arfind.firebase.dto.PlanDto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    private Context context;
    private List<PlanDto> planList;

    public PlanAdapter(Context context, List<PlanDto> planList) {
        this.context = context;
        this.planList = planList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_plan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanDto plan = planList.get(position);
        holder.txtTitulo.setText(plan.getTitulo());
        holder.txtSubtitulo.setText(plan.getDescripcion());
        // Aquí puedes cargar la imagen utilizando alguna librería como Picasso o Glide
        // Por ejemplo:
        String imageUrl = plan.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.imgItem);
        } else {
            holder.imgItem.setImageResource(R.drawable.img_dispositivo_back);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BotonUtil.INSTANCE.deshabilitarPorTiempo(holder.itemView);
                //Intent intent = new Intent(context, DetailAddActivity.class);
                //intent.putExtra("tipo", 2);
                //intent.putExtra("consumibleId", chicken.getId());
                //Activity activity = (Activity) context;
                //activity.overridePendingTransition(R.anim.slide_out_left, 0);
                //activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtSubtitulo;
        ImageView imgItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.plan_titulo);
            txtSubtitulo = itemView.findViewById(R.id.plan_descripcion);
            imgItem = itemView.findViewById(R.id.img_item_plan);
        }
    }
}
