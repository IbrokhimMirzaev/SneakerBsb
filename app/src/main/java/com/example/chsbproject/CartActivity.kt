package com.example.chsbproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chsbproject.adapter.CartAdapter
import com.example.chsbproject.databinding.ActivityCartBinding
import com.example.chsbproject.model.Sneaker

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: CartAdapter
    var total: Double = 60.20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sneakers = intent.getSerializableExtra("cart_sneakers") as MutableList<Sneaker>
        var addedToCartSneakers = sneakers.filter { it.isAddedToCart }.toMutableList()

        binding.back.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            for (i in 0 until sneakers.size) {
                if (!addedToCartSneakers.contains(sneakers[i])) {
                    sneakers[i].isAddedToCart = false
                }
            }
            intent.putExtra("added", sneakers as java.io.Serializable)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        adapter = CartAdapter(this, addedToCartSneakers, object: CartAdapter.MyInterface {
            @SuppressLint("SetTextI18n")
            override fun myInterfaceFunction(subTotal: Double) {
                total = subTotal + 60.20

                binding.totalCost.text = total.toString()
                binding.subTotal.text = subTotal.toString()
            }

            override fun update() {
                binding.itemCount.visibility = getVisibility(addedToCartSneakers, this@CartActivity)
                binding.bottomPrices.visibility = getVisibility(addedToCartSneakers, this@CartActivity)
                binding.rv.visibility = getVisibility(addedToCartSneakers, this@CartActivity)

                binding.lottieEmpty.visibility = if (getVisibility(addedToCartSneakers, this@CartActivity) != View.VISIBLE) View.VISIBLE else View.GONE
                binding.emptyString.visibility = if (getVisibility(addedToCartSneakers, this@CartActivity) != View.VISIBLE) View.VISIBLE else View.GONE
            }
        })

        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = adapter

        binding.itemCount.text = "${addedToCartSneakers.size} ${getString(R.string.item)}"
        binding.delivery.text = "$ 60.20"

        binding.itemCount.visibility = getVisibility(addedToCartSneakers, this@CartActivity)
        binding.bottomPrices.visibility = getVisibility(addedToCartSneakers, this@CartActivity)
        binding.rv.visibility = getVisibility(addedToCartSneakers, this@CartActivity)

        binding.lottieEmpty.visibility = if (getVisibility(addedToCartSneakers, this@CartActivity) != View.VISIBLE) View.VISIBLE else View.GONE
        binding.emptyString.visibility = if (getVisibility(addedToCartSneakers, this@CartActivity) != View.VISIBLE) View.VISIBLE else View.GONE

    }

    private fun getVisibility(addedToCartSneakers: MutableList<Sneaker>, context: Context): Int {
        return if (addedToCartSneakers.isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}