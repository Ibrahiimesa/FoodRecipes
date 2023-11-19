package com.esa.foodrecipes.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esa.foodrecipes.data.Repository
import com.esa.foodrecipes.data.Resource
import com.esa.foodrecipes.data.response.FilterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _foodList = MutableLiveData<Resource<FilterResponse>>()
    val foodList: LiveData<Resource<FilterResponse>> get() = _foodList

    fun fetchSearchFoodList(query: String) {
        viewModelScope.launch {
            _foodList.value = Resource.Loading()
            _foodList.value = repository.searchFood(query)
        }
    }

    fun fetchFoodList(category: String) {
        viewModelScope.launch {
            _foodList.value = Resource.Loading()
            _foodList.value = repository.getFoodFilter(category)
        }
    }
}