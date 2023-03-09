package com.example.chsbproject

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import com.example.chsbproject.databinding.ActivityCodeBinding
import java.time.Duration

class CodeActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityCodeBinding

    private var selectedNumbers: Int = -1
    var pinCode: String = ""
    var plots = mutableListOf<View>()

    lateinit var shared: SharedPreferences
    lateinit var edit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shared = getSharedPreferences("login", MODE_PRIVATE)
        edit = shared.edit()

        addAllPlots()

        binding.dot1.setOnClickListener(this)
        binding.dot2.setOnClickListener(this)
        binding.dot3.setOnClickListener(this)
        binding.dot4.setOnClickListener(this)
        binding.dot5.setOnClickListener(this)
        binding.dot6.setOnClickListener(this)
        binding.dot7.setOnClickListener(this)
        binding.dot8.setOnClickListener(this)
        binding.dot9.setOnClickListener(this)
        binding.zero.setOnClickListener(this)

        binding.delete.setOnClickListener {
            if (selectedNumbers != -1) {
                selectedNumbers--
                pinCode = pinCode.dropLast(1)
                updatePlotColors(false)
            }
        }
    }

    override fun onClick(p0: View?) {
        val btn = findViewById<Button>(p0!!.id)

        if (selectedNumbers < plots.size - 1) {
            selectedNumbers++
            pinCode += btn.text.toString()
        }

        if (selectedNumbers == plots.size - 1) {
            if (shared.getString("pin_code", "") == "") {
                edit.putString("pin_code", pinCode).apply()

                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(
                    this,
                    "Your pin code: ${shared.getString("pin_code", "")}",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                if (shared.getString("pin_code", "").equals(pinCode)) {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Pin code is correct!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Pin code is incorrect!", Toast.LENGTH_SHORT).show()
                    selectedNumbers = -1
                    pinCode = ""
                }
            }
        }

        updatePlotColors()
    }

    private fun addAllPlots() {
        plots.add(binding.plot1)
        plots.add(binding.plot2)
        plots.add(binding.plot3)
        plots.add(binding.plot4)
    }

    private fun updatePlotColors(boolean: Boolean = true) {
        for (i in 0 until plots.size) {
            if (i <= selectedNumbers) {
                plots[i].setBackgroundResource(R.drawable.green_oval)
            }
            else {
                plots[i].setBackgroundResource(R.drawable.oval_code)
            }
        }

        if (selectedNumbers == -1 && boolean) {
            Handler().postDelayed({
                plots.forEach { it.setBackgroundResource(R.drawable.oval_code) }
            }, 500)
            plots.forEach { it.setBackgroundResource(R.drawable.error_code) }
        }
    }
}