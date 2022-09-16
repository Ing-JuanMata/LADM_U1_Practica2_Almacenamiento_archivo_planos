package mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(titles: ArrayList<String>, datas: ArrayList<String>) : RecyclerView
.Adapter<CustomAdapter
.ViewHolder>() {

    val titles = titles

    val datas = datas

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.card_layout, viewGroup,
            false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tituloPedido.text = titles.get(i)
        viewHolder.datosPedido.text = datas.get(i)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tituloPedido: TextView
        var datosPedido: TextView

        init {
            tituloPedido = itemView.findViewById(R.id.tituloPedido)
            datosPedido = itemView.findViewById(R.id.datosPedido)

        }
    }
}