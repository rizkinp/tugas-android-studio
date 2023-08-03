package com.example.appc09_tugas

\
import android.view.View
import android.widget.*


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_film_info.*

class FilmInfoActivity : AppCompatActivity() {

    private var isEditing: Boolean = false
    private lateinit var titleTextView: TextView
    private lateinit var detailTextView: TextView
    private lateinit var editTitleTextView: TextView
    private lateinit var editTitleEditText: EditText
    private lateinit var editDetailTextView: TextView
    private lateinit var editDetailEditText: EditText
    private lateinit var titleColorSpinner: Spinner
    private lateinit var titleSizeSeekBar: SeekBar
    private lateinit var detailSizeSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_info)

        titleTextView = findViewById(R.id.titleTextView)
        detailTextView = findViewById(R.id.detailTextView)
        editTitleTextView = findViewById(R.id.editTitleTextView)
        editTitleEditText = findViewById(R.id.editTitleEditText)
        editDetailTextView = findViewById(R.id.editDetailTextView)
        editDetailEditText = findViewById(R.id.editDetailEditText)
        titleColorSpinner = findViewById(R.id.titleColorSpinner)
        titleSizeSeekBar = findViewById(R.id.titleSizeSeekBar)
        detailSizeSeekBar = findViewById(R.id.detailSizeSeekBar)

        titleColorSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.color_array,
            android.R.layout.simple_spinner_item
        )

        titleColorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val color = parent?.getItemAtPosition(position).toString()
                val colorCode = Color.parseColor(color)
                titleTextView.setTextColor(colorCode)
                editTitleEditText.setTextColor(colorCode)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        titleSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val textSize = progress + 16
                titleTextView.textSize = textSize.toFloat()
                editTitleEditText.textSize = textSize.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        detailSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val textSize = progress + 12
                detailTextView.textSize = textSize.toFloat()
                editDetailEditText.textSize = textSize.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_film_info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editFilmInfoMenuItem -> {
                if (isEditing) {
                    // Update the info
                    val title = editTitleEditText.text.toString()
                    val detail = editDetailEditText.text.toString()
                    titleTextView.text = title
                    detailTextView.text = detail

                    // Hide the edit views and show the info views
                    editTitleTextView.visibility = TextView.GONE
                    editTitleEditText.visibility = EditText.GONE
                    editDetailTextView.visibility = TextView.GONE
                    editDetailEditText.visibility = EditText.GONE
                    titleTextView.visibility = TextView.VISIBLE
                    detailTextView.visibility = TextView.VISIBLE

                    // Change the edit menu item to "Edit"
                    item.title = "Edit"
                } else {
                    // Show the edit views and hide the info views
                    editTitleEditText.setText(titleTextView.text)
                    editDetailEditText.setText(detailTextView.text)
                    editTitleTextView.visibility = TextView.VISIBLE
                    editTitleEditText.visibility = EditText.VISIBLE
                    editDetailTextView.visibility = TextView.VISIBLE
                    editDetailEditText.visibility = EditText.VISIBLE
                    titleTextView.visibility = TextView.GONE
                    detailTextView.visibility = TextView.GONE

                    // Change the edit menu item to "Save"
                    item.title = "Save"
                }
                isEditing = !isEditing
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}