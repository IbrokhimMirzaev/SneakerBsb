package com.example.chsbproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chsbproject.databinding.ActivityRegisterBinding
import com.example.chsbproject.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private var userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shared: SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val edit = shared.edit()
        val gson = Gson()
        val convert = object : TypeToken<List<User>>() {}.type


        binding.signUpBtn.setOnClickListener {
            var users = shared.getString("users", "")
            if (users == "") {
                userList = mutableListOf()
            } else {
                userList = gson.fromJson(users, convert)
            }

            if (validate()) {
                userList.add(User(binding.nameInput.text.toString(), binding.passwordInput.text.toString()))

                val str = gson.toJson(userList)
                edit.putString("users", str).apply()

                Toast.makeText(this,"Successfully registered",Toast.LENGTH_SHORT).show()

                var intent = Intent(this, CodeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validate(): Boolean {
        if (binding.nameInput.text.toString().isEmpty() || binding.passwordInput.text.toString().isEmpty()) {
            Toast.makeText(this, "Fill the form fully", Toast.LENGTH_SHORT).show()
            return false
        }
        for (i in userList.indices) {
            if (binding.nameInput.text.toString() == userList[i].name) {
                Toast.makeText(this, "User with this username already registered", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }
}