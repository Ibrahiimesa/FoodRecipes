package com.esa.foodrecipes.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esa.foodrecipes.data.Repository
import com.esa.foodrecipes.data.response.DetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _foodDetail = MutableLiveData<DetailResponse>()
    val foodDetail: LiveData<DetailResponse> get() = _foodDetail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchMealDetails(id: String) {
        viewModelScope.launch {
            try {
                val detailResponse = repository.getDetail(id)
                withContext(Dispatchers.Main) {
                    _foodDetail.value = detailResponse
                }
            } catch (e: Exception) {
                // Handle errors
                withContext(Dispatchers.Main) {
                    _error.value = "Failed to fetch food details"
                }
            }
        }
    }
}