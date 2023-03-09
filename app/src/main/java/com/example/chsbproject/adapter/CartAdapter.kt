package com.example.chsbproject.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.RecyclerView
import com.example.chsbproject.CartActivity
import com.example.chsbproject.R
import com.example.chsbproject.model.Sneaker

class CartAdapter(
    var context: Context,
    var sneakers: MutableList<Sneaker>,
    var myInterface: MyInterface
) : RecyclerView.Adapter<CartAdapter.CartHolder>() {
    interface MyInterface {
        fun myInterfaceFunction(subTotal: Double)
        fun update()
    }

    class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgCart)
        val name: TextView = itemView.findViewById(R.id.nameCart)
        val price: TextView = itemView.findViewById(R.id.priceCart)
        val count: TextView = itemView.findViewById(R.id.countCart)
        val plusBtn: ImageView = itemView.findViewById(R.id.plusCart)
        val minusBtn: ImageView = itemView.findViewById(R.id.minusCart)
        val sub: TextView = itemView.findViewById(R.id.subTotalPrice)
        val deleteBtn: TextView = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        return CartHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_shoe_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        var subTotal = 0.0
        var sneaker: Sneaker = sneakers[position]

        holder.img.setImageResource(sneaker.img)
        holder.name.text = sneaker.name
        holder.price.text = "$${sneaker.price}"

        holder.plusBtn.setOnClickListener {
            sneaker.count++
            sneaker.subTotal = sneaker.count * sneaker.price
            notifyItemChanged(position)
        }

        holder.minusBtn.setOnClickListener {
            if (sneaker.count > 1) {
                sneaker.count--
            }
            sneaker.subTotal = sneaker.count * sneaker.price
            notifyItemChanged(position)
        }

        for (i in 0 until sneakers.size) {
            subTotal += sneakers[i].subTotal
        }

        myInterface.myInterfaceFunction(subTotal)

        holder.count.text = sneaker.count.toString()
        holder.sub.text = "$${sneaker.subTotal}"

        holder.deleteBtn.setOnClickListener {
            sneakers.removeAt(position)
            if (sneakers.size==0){
                myInterface.update()
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return sneakers.size
    }

}