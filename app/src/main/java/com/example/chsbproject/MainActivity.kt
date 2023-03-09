package com.example.chsbproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chsbproject.adapter.SneakerAdapter
import com.example.chsbproject.databinding.ActivityMainBinding
import com.example.chsbproject.model.Sneaker
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var selectedItems = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadLocale()
        loadIndex()

        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sneakers = mutableListOf<Sneaker>()

        if (intent.hasExtra("updateSneakers")) {
            sneakers = intent.getSerializableExtra("updateSneakers") as MutableList<Sneaker>
        }
        if (intent.hasExtra("added")) {
            sneakers = intent.getSerializableExtra("added") as MutableList<Sneaker>
        }


        var count = 0
        for (i in 1..25) {
            if (count % 3 == 0) {
                count = 0
            }

            if (count == 0) {
                sneakers.add(Sneaker(false, R.drawable.img_1, "Nike Jordan", 302.0, false))
            }
            if (count == 1) {
                sneakers.add(Sneaker(false, R.drawable.img_2, "Nike Air Max", 752.0, false))
            }
            if (count == 2) {
                sneakers.add(Sneaker(false, R.drawable.img_3, "Nike Zoom Moc", 586.0, false))
            }

            count++
        }

        filterFavourites(false, sneakers)

        binding.cartBtn.setOnClickListener {
            var intent = Intent(this, CartActivity::class.java)
            intent.putExtra("cart_sneakers", sneakers as java.io.Serializable)
            startActivity(intent)
        }

        var myBoolean = true
        binding.favAppBarBtn.setOnClickListener {
            filterFavourites(myBoolean, sneakers)
            myBoolean = !myBoolean
        }

        binding.editText.addTextChangedListener {
            var list: MutableList<Sneaker> = if (!myBoolean) {
                sneakers.filter { it.isFavourite }.toMutableList()
            } else {
                sneakers
            }
            if (binding.editText.text.toString().isNotEmpty()) {
                var filterSneakers: MutableList<Sneaker> = mutableListOf()
                for (i in list) {
                    if (i.name.lowercase().trim()
                            .contains(binding.editText.text.toString().lowercase().trim())
                    ) {
                        filterSneakers.add(i)
                    }
                }
                val filter = SneakerAdapter(this, filterSneakers)
                binding.rv.adapter = filter
            } else {
                val ad = SneakerAdapter(this, list)
                binding.rv.adapter = ad
            }
        }

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.editText.clearFocus()
            }
            false
        }

        binding.changeLangBtn.setOnClickListener {
            showChangeLang()
        }
    }

    private fun filterFavourites(boolean: Boolean, sneakers: MutableList<Sneaker>) {
        if (boolean) {
            binding.favAppBarBtn.setImageResource(R.drawable.fav)
            binding.nikeShoes.text = getString(R.string.favourites)
            if (sneakers.filter { it.isFavourite }.toMutableList().isEmpty()) {
                binding.noFavsLottie.visibility = View.VISIBLE
                binding.noFavsText.visibility = View.VISIBLE
                binding.constRv.visibility = View.GONE
                binding.editLay.visibility = View.GONE
            } else {
                changeLayout1()

                val ad = SneakerAdapter(this, sneakers.filter { it.isFavourite }.toMutableList())
                binding.rv.layoutManager = GridLayoutManager(this, 2)
                binding.rv.adapter = ad
            }
        } else {
            binding.favAppBarBtn.setImageResource(R.drawable.fav_border)
            binding.nikeShoes.text = getString(R.string.nike_shoes)
            changeLayout1()
            val adapter = SneakerAdapter(this, sneakers)
            binding.rv.layoutManager = GridLayoutManager(this, 2)
            binding.rv.adapter = adapter
        }
    }

    private fun changeLayout1() {
        binding.noFavsLottie.visibility = View.GONE
        binding.noFavsText.visibility = View.GONE
        binding.constRv.visibility = View.VISIBLE
        binding.editLay.visibility = View.VISIBLE
    }

    private fun showChangeLang() {
        val listItems = arrayOf("English", "Uzbek")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(getString(R.string.choose))
        mBuilder.setSingleChoiceItems(listItems, selectedItems) { dialog, which ->
            if (which == 0) {
                setLocale("en-rUM")
                recreate()
            } else if (which == 1) {
                setLocale("uz")
                recreate()
            }

            setIndex(which)

            dialog.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocale(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocale(language!!)
    }

    private fun setIndex(selectedIndex: Int) {
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putInt("index", selectedIndex)
        editor.apply()
    }

    private fun loadIndex() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        selectedItems = sharedPreferences.getInt("index", 0)
    }
}