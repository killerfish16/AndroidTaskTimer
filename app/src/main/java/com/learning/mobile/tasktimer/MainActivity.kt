package com.learning.mobile.tasktimer

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        //testInsert()
        //testUpdate()
        //testUpdate2()
        testDelete2()
        //testDelete()
        val projection = arrayOf(TasksContract.Columns.TASK_NAME,TasksContract.Columns.TASK_SORT_ORDER)
        val sortOrder = TasksContract.Columns.TASK_SORT_ORDER
        val cursor = contentResolver.query(
            //TasksContract.buildUriFromId(2),
            TasksContract.CONTENT_URI,
            null,
            null,
            null,
            sortOrder
        )

        Log.d(TAG,"********************************************")
        cursor.use {
            while (it!!.moveToNext()){
                with(cursor){
                    val id = this?.getLong(0)
                    val name = this?.getString(1)
                    val description = this?.getString(2)
                    val colSortOrder = this?.getString(3)
                    val result = "id: $id, name: $name, description: $description, sortOrder: $colSortOrder"
                    Log.d(TAG, "onCreate: reading data $result")
                }

            }
        }
        Log.d(TAG,"********************************************")
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun testInsert(){
        Log.d("TAG", "testInsert starts...!")
        val values = ContentValues().apply{
            put(TasksContract.Columns.TASK_NAME, "New Task1")
            put(TasksContract.Columns.TASK_DESCRIPTION, "New description")
            put(TasksContract.Columns.TASK_SORT_ORDER, 2)
        }

        val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)

        Log.d(TAG, "New row id in uri i : $uri")
        Log.d(TAG,"id (in uri) is ${uri?.let { TasksContract.getId(it) }}")
    }

    private fun testUpdate(){
        Log.d("TAG", "testUpdate starts...!")
        val values = ContentValues().apply{
            put(TasksContract.Columns.TASK_NAME, "Content provider")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Record content provider")
        }
        val taskUri = TasksContract.buildUriFromId(4)
        val rowAffected = contentResolver.update(taskUri, values, null, null)

        Log.d(TAG, "No of rows updated is : $rowAffected")

    }

    private fun testUpdate2(){
        Log.d("TAG", "testUpdate2 starts...!")
        val values = ContentValues().apply{
            put(TasksContract.Columns.TASK_SORT_ORDER, 999)
            put(TasksContract.Columns.TASK_DESCRIPTION, "Completed")
        }

        val selection = TasksContract.Columns.TASK_SORT_ORDER + " = ?"
        val selectionArgs = arrayOf("99")
        //val taskUri = TasksContract.buildUriFromId(4)
        val rowAffected = contentResolver.update(TasksContract.CONTENT_URI, values, selection, selectionArgs)

        Log.d(TAG, "No of rows updated  (testUpdate2) is : $rowAffected")

    }

    private fun testDelete(){
        Log.d("TAG", "testDelete starts...!")
        val taskUri = TasksContract.buildUriFromId(3)
        val rowAffected = contentResolver.delete(taskUri, null, null)
        Log.d(TAG, "No of rows delete is : $rowAffected")
    }

    private fun testDelete2(){
        Log.d("TAG", "testDelete2 starts...!")

        val selection = TasksContract.Columns.TASK_SORT_ORDER + " = ?"
        val selectionArgs = arrayOf("999")
        //val taskUri = TasksContract.buildUriFromId(4)
        val rowAffected = contentResolver.delete(TasksContract.CONTENT_URI, selection, selectionArgs)

        Log.d(TAG, "No of rows deleted  (testUpdate2) is : $rowAffected")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.mainMenu_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}