package com.example.appc06_sql

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProgramStudiViewModel(application: Application) : AndroidViewModel(application) {

    private val programStudiDao: ProgramStudiDao = ProgramStudiRoomDatabase.getDatabase(application).programStudiDao()

    private val _programStudiList: MutableLiveData<List<ProgramStudi>> = MutableLiveData()
    val programStudiList: LiveData<List<ProgramStudi>>
        get() = _programStudiList

    fun getAllProgramStudis() {
        viewModelScope.launch {
            _programStudiList.postValue(programStudiDao.getAllProgramStudis())
        }
    }

    fun insertProgramStudi(programStudi: ProgramStudi) {
        viewModelScope.launch {
            programStudiDao.insertProgramStudi(programStudi)
            getAllProgramStudis()
        }
    }

    fun updateProgramStudi(programStudi: ProgramStudi) {
        viewModelScope.launch {
            programStudiDao.updateProgramStudi(programStudi)
            getAllProgramStudis()
        }
    }

    fun deleteProgramStudi(programStudi: ProgramStudi) {
        viewModelScope.launch {
            programStudiDao.deleteProgramStudi(programStudi)
            getAllProgramStudis()
        }
    }
}
