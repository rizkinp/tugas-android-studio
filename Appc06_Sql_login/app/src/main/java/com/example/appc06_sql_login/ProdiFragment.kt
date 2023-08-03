package com.example.appc06_sql_login

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProdiFragment : Fragment(), View.OnClickListener {
    private var editTextName: EditText? = null
    private var editTextAddress: EditText? = null
    private var buttonInsert: Button? = null
    private var buttonUpdate: Button? = null
    private var buttonDelete: Button? = null
    private var dbHelper: DBHelper? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_prodi, container, false)
        editTextName = view.findViewById(R.id.editTextName)
        editTextAddress = view.findViewById(R.id.editTextAddress)
        buttonInsert = view.findViewById(R.id.buttonInsert)
        buttonUpdate = view.findViewById(R.id.buttonUpdate)
        buttonDelete = view.findViewById(R.id.buttonDelete)
        buttonInsert.setOnClickListener(this)
        buttonUpdate.setOnClickListener(this)
        buttonDelete.setOnClickListener(this)
        dbHelper = DBHelper(getActivity())
        return view
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.buttonInsert -> {
                val name = editTextName!!.text.toString()
                val address = editTextAddress!!.text.toString()
                dbHelper?.insertData(name, address)
                Toast.makeText(getActivity(), "Data inserted successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            R.id.buttonUpdate -> {
                val id: Int = editTextId.getText().toString().toInt()
                name = editTextName!!.text.toString()
                address = editTextAddress!!.text.toString()
                dbHelper.updateData(id, name)
                Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            R.id.buttonDelete -> {
                id = editTextId.getText().toString().toInt()
                dbHelper.deleteData(id)
                Toast.makeText(getActivity(), "Data deleted successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
