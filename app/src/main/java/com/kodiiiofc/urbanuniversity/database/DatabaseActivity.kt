package com.kodiiiofc.urbanuniversity.database

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DatabaseActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var nameET: EditText
    private lateinit var occupationSp: Spinner
    private lateinit var phoneET: EditText
    private lateinit var saveBTN: Button
    private lateinit var getDataBTN: Button
    private lateinit var removeDataBTN: Button
    private lateinit var databaseOutputTV: TextView

    private val db = DBHelper(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        initEditableFields()
        initButtons()



        saveBTN.setOnClickListener {
            saveData()
        }

        getDataBTN.setOnClickListener {
            getData()
        }

        removeDataBTN.setOnClickListener {
            removeDataBase()
        }

    }

    private fun initButtons() {
        saveBTN = findViewById(R.id.btn_save)
        getDataBTN = findViewById(R.id.btn_get_data)
        removeDataBTN = findViewById(R.id.btn_remove_data)
    }

    private fun initEditableFields() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.occupations, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nameET = findViewById(R.id.et_name)
        occupationSp = findViewById(R.id.sp_occupation)
        occupationSp.adapter = adapter
        phoneET = findViewById(R.id.et_phone)
        databaseOutputTV = findViewById(R.id.tv_database_output)
    }

    private fun removeDataBase() {
        db.removeAll()
        clearEditableFields()
        databaseOutputTV.text = ""
    }

    private fun getData() {
        val cursor = db.getInfo()

        var counter = 1
        var name: String
        var occupation: String
        var phone: String

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst()

            val nameColumnIndex = cursor.getColumnIndex(DBHelper.KEY_NAME)
            val occupationColumnIndex = cursor.getColumnIndex(DBHelper.KEY_OCCUPATION)
            val phoneColumnIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE)

            if (nameColumnIndex * occupationColumnIndex * phoneColumnIndex < 0) {
                databaseOutputTV.text = "База данных пуста"
                cursor.close()
                return
            }

            do {
                name = cursor.getString(nameColumnIndex)
                occupation = cursor.getString(occupationColumnIndex)
                phone = cursor.getString(phoneColumnIndex)
                databaseOutputTV.append("$counter. $name, $occupation, $phone\n")
                counter++
            } while (cursor.moveToNext())

            cursor.close()
        }

    }

    private fun saveData() {
        val person = Person(nameET.text.toString(),
            occupationSp.selectedItem.toString(),
            phoneET.text.toString())
        db.add(person)
        Toast.makeText(this, "Person ${person.name} saved", Toast.LENGTH_SHORT).show()
        clearEditableFields()
    }

    private fun clearEditableFields() {
        nameET.text.clear()
        phoneET.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}