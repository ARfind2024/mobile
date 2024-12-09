package com.liontail.arfind.planes


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liontail.arfind.R
import com.squareup.picasso.Picasso

class PlanesAdapter(
    private val planes: List<PlanesDto>,
    private val onPlanSelected: (PlanesDto) -> Unit,
) : RecyclerView.Adapter<PlanesAdapter.PlanesViewHolder>() {

    private var selectedPlan: PlanesDto? = null

    inner class PlanesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkbox_plan)
        val nombrePlan: TextView = view.findViewById(R.id.txt_nombre_plan)
        val descripcionPlan: TextView = view.findViewById(R.id.txt_descripcion_plan)
        val precioPlan: TextView = view.findViewById(R.id.txt_precio_plan)
        val imgPlan: ImageView = view.findViewById(R.id.img_plan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plan, parent, false)
        return PlanesViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: PlanesViewHolder, position: Int) {
        val plan = planes[position]

        Picasso.get()
            .load(plan.imagen)
            .placeholder(R.drawable.arfind_logo)
            .error(R.drawable.img_dispositivo_back)
            .into(holder.imgPlan)

        holder.nombrePlan.text = plan.nombre
        holder.descripcionPlan.text = plan.descripcion
        holder.precioPlan.text = "$" + plan.precio.toString()

        holder.checkBox.isChecked = (plan == selectedPlan)

        if (plan == selectedPlan) {
            holder.checkBox.buttonTintList = ColorStateList.valueOf(R.color.blueArfind)
        } else {
            holder.checkBox.buttonTintList = ColorStateList.valueOf(Color.GRAY)
        }

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedPlan = plan
                onPlanSelected(plan)
                notifyDataSetChanged()
            } else if (plan == selectedPlan) {
                selectedPlan = null
                onPlanSelected(plan)
            }
        }
    }

    override fun getItemCount(): Int = planes.size
}




