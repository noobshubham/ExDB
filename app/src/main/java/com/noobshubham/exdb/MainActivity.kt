package com.noobshubham.exdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.noobshubham.exdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: PersonDatabase
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = PersonDatabase.getDatabase(this)
        binding.btnAddPersonInfo.setOnClickListener { addPersonInfo() }
    }

    private fun addPersonInfo() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val age = binding.etAge.text.toString()
        val gender = binding.etGender.text.toString()
        val email = binding.etEmail.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() && email.isNotEmpty()) {
            val person = Person(0, firstName, lastName, age.toInt(), gender, email)

            GlobalScope.launch(Dispatchers.IO) { database.personDao().insertPerson(person) }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etAge.text.clear()
            binding.etGender.text.clear()
            binding.etEmail.text.clear()

            Toast.makeText(this, "Successfully Added Person to DB", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
    }
}