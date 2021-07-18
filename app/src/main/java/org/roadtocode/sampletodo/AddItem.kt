package org.roadtocode.sampletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddItem : AppCompatActivity()
{
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        mDatabase = FirebaseDatabase.getInstance().reference

        val etTaskName: EditText = findViewById(R.id.etTaskName)
        val etDescription: EditText = findViewById(R.id.etDescription)

        val btnAddTask: Button = findViewById(R.id.btnAddTask)

        btnAddTask.setOnClickListener {

            val newItem = mDatabase.child("todoList").push()

                val taskItem =
                newItem.key?.let {
                        it1 -> TaskItem(it1,etTaskName.text.toString(), etDescription.text.toString())
                }

            newItem.setValue(taskItem)

            Toast.makeText(this, "Task Added Successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this,MainActivity::class.java)


            startActivity(intent)
        }
    }
}