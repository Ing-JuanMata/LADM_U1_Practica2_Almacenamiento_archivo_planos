package mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//La clase recibe los datos que serán usados para dibujar la vista
class CustomAdapter(
    val titles: ArrayList<String>, val datas: ArrayList<String>, private val
    clickListener: onDataClick
) : RecyclerView
.Adapter<CustomAdapter
.ViewHolder>() {

    //region Interface para el manejo de clicks
    interface onDataClick {
        fun onClick(pos: Int)
    }
    //endregion
    //region Funciones obligatorias
    //region Funcion para cuando se crea la vista
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.card_layout, viewGroup,
            false
        )
        return ViewHolder(v)
    }
    //endregion

    //region Función para cuando se rellena la vista
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tituloPedido.text = titles.get(i)
        viewHolder.datosPedido.text = datas.get(i)
    }
    //endregion

    //region Cantidad de elementos en la vista
    override fun getItemCount(): Int {
        return titles.size
    }
    //endregion
    //endregion

    //region Clase interna para formar la vista
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tituloPedido: TextView
        var datosPedido: TextView

        init {
            tituloPedido = itemView.findViewById(R.id.tituloPedido)
            datosPedido = itemView.findViewById(R.id.datosPedido)
            itemView.setOnClickListener { clickListener.onClick(adapterPosition) }

        }
    }
    //endregion


}