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

import com.liontail.arfind.DetalleProductoActivity;
import com.liontail.arfind.R;
import com.liontail.arfind.productos.ProductoDto;
import com.liontail.arfind.utils.BotonUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private Context context;
    private List<ProductoDto> productoList;

    public ProductoAdapter(Context context, List<ProductoDto> planList) {
        this.context = context;
        this.productoList = planList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_producto, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductoDto producto = productoList.get(position);
        holder.txtTitulo.setText(producto.getTitulo());
        holder.txtSubtitulo.setText(producto.getDescripcion());
        // Aquí puedes cargar la imagen utilizando alguna librería como Picasso o Glide
        // Por ejemplo:
        String imageUrl = producto.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.imgItem);
        } else {
            holder.imgItem.setImageResource(R.drawable.img_dispositivo_back);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotonUtil.INSTANCE.deshabilitarPorTiempo(holder.itemView);
                Intent intent = new Intent(context, DetalleProductoActivity.class);
                intent.putExtra("producto_position", position);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.slide_out_left, 0);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productoList.size();
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
