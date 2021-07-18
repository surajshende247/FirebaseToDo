package org.roadtocode.sampletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    var taskList: MutableList<TaskItem>? = null
    lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance().reference

        val fabAdd: FloatingActionButton = findViewById(R.id.btnAdd)
        fabAdd.setOnClickListener {
            val intent = Intent(this,AddItem::class.java)
            startActivity(intent)
        }


        taskList = mutableListOf<TaskItem>()
        val recyclerView: RecyclerView? = findViewById(R.id.recyclerView)

        recyclerView!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        adapter = CustomAdapter(taskList)
        recyclerView!!.adapter = adapter

        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)


        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(recyclerView)

    }
    val itemListener: ValueEventListener = object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val items = snapshot.children.iterator()
            if(items.hasNext())
            {
                val taskNextIndex = items.next()
                val eachTaskIterator = taskNextIndex.children.iterator()

                while(eachTaskIterator.hasNext())
                {
                    val currentTask = eachTaskIterator.next()
                    val map = currentTask.getValue() as HashMap<String, Any>

                    val taskItem = TaskItem(
                        map.get("taskId") as String,
                        map.get("taskName") as String,
                        map.get("taskDescription") as String
                    )
                    taskList?.add(taskItem)
                }
            }
            adapter.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    val myCallback = object: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.RIGHT) {


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewHolder.adapterPosition
            mDatabase.child("todoList").child(taskList!!.get(viewHolder.adapterPosition).taskId).removeValue()
        }

    }


}