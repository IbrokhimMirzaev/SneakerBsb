package com.example.chsbproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chsbproject.databinding.ActivityCartBinding
import com.example.chsbproject.databinding.ActivitySneakerDetailBinding
import com.example.chsbproject.model.Sneaker

class SneakerDetail : AppCompatActivity() {
    lateinit var binding: ActivitySneakerDetailBinding
    lateinit var sneakers: MutableList<Sneaker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySneakerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("updateSneakers", sneakers as java.io.Serializable)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        sneakers = intent.getSerializableExtra("sneakers") as MutableList<Sneaker>
        var index: Int = intent.getIntExtra("index", 0)
        var sneaker = sneakers[index]

        binding.sneakerImg.setImageResource(sneaker.img)
        binding.sneakerName.text = sneaker.name
        binding.sneakerPrice.text = "$ ${sneaker.price}"

        if (sneakers[index].isFavourite) {
            binding.addToFavBtn.setImageResource(R.drawable.fav)
        } else {
            binding.addToFavBtn.setImageResource(R.drawable.fav_border)
        }

        binding.addToFavBtn.setOnClickListener {
            sneakers[index].isFavourite = !sneakers[index].isFavourite

            if (sneakers[index].isFavourite) {
                binding.addToFavBtn.setImageResource(R.drawable.fav)
            } else {
                binding.addToFavBtn.setImageResource(R.drawable.fav_border)
            }

        }

        binding.addToCartBtn.setOnClickListener {
            sneakers[index].isAddedToCart = true
        }
    }

    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("updateSneakers", sneakers as java.io.Serializable)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}