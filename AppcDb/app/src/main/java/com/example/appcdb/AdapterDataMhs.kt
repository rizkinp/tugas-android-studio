package com.example.appcdb

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.appcdb.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class AdapterDataMhs(val dataMhs :
                     List<HashMap<String,String>>,
                     val mainActivity: MainActivity,
                     val b : ActivityMainBinding
) :
    RecyclerView.Adapter<AdapterDataMhs.HolderDataMhs>(){

    inner class HolderDataMhs(v : View) :
        RecyclerView.ViewHolder(v){
        val txNim = v.findViewById<TextView>(R.id.txNim)
        val txNama = v.findViewById<TextView>(R.id.txNama)
        val txProdi = v.findViewById<TextView>(R.id.txProdi)
        val photo = v.findViewById<ImageView>(R.id.imageView)
        val cLayout = v.findViewById<ConstraintLayout>(R.id.cLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDataMhs.HolderDataMhs {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_mhs,parent,false)
        return HolderDataMhs(v)
    }

    override fun getItemCount(): Int {
        return dataMhs.size
    }

    override fun onBindViewHolder(holder: AdapterDataMhs.HolderDataMhs, position: Int) {
        val data = dataMhs.get(position)
        holder.txNim.setText(data.get("nim"))
        holder.txNama.setText(data.get("nama"))
        holder.txProdi.setText(data.get("nama_prodi"))

        if(position.rem(2) == 0)
            holder.cLayout.setBackgroundColor(Color.rgb(230,245,240))
        else
            holder.cLayout.setBackgroundColor(Color.rgb(255,255,245))

        holder.cLayout.setOnClickListener(View.OnClickListener {
            val pos = mainActivity.daftarProdi.indexOf(
                data.get("nama_prodi"))
            b.spinProdi.setSelection(pos)
            b.edNim.setText(data.get("nim"))
            b.edNamaMhs.setText (data.get("nama"))
            Picasso.get().load(data.get("url")).into(
                b.imUpload);
        })
        if(!data.get("url").equals(""))
            Picasso.get().load(data.get("url")).into(holder.photo)
    }
}