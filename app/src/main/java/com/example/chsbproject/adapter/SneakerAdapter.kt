package com.example.chsbproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.chsbproject.R
import com.example.chsbproject.SneakerDetail
import com.example.chsbproject.model.Sneaker

class SneakerAdapter(var context: Context, var sneakers: MutableList<Sneaker>) : RecyclerView.Adapter<SneakerAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val favBtn: ImageView = itemView.findViewById(R.id.favBtn)
        val plusBtn: ConstraintLayout = itemView.findViewById(R.id.plusBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.sneaker_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var sneaker: Sneaker = sneakers[position]

        holder.img.setImageResource(sneaker.img)
        holder.name.text = sneaker.name
        holder.price.text = "$ ${sneaker.price}"

        holder.favBtn.setOnClickListener {
            sneaker.isFavourite = !sneaker.isFavourite

            if (sneaker.isFavourite) {
                holder.favBtn.setBackgroundResource(R.drawable.fav)
            }
            else {
                holder.favBtn.setBackgroundResource((R.drawable.fav_border))
            }
        }

        if (sneaker.isFavourite) {
            holder.favBtn.setBackgroundResource(R.drawable.fav)
        }
        else {
            holder.favBtn.setBackgroundResource((R.drawable.fav_border))
        }

        holder.plusBtn.setOnClickListener {
            sneaker.isAddedToCart = true

            Toast.makeText(context, "Savatchaga qo'shildi", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnClickListener {
            var intent = Intent(context, SneakerDetail::class.java)
            intent.putExtra("sneakers", sneakers as java.io.Serializable)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return sneakers.size
    }
}