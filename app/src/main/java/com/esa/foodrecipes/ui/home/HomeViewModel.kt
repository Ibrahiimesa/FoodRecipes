package com.esa.foodrecipes.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esa.foodrecipes.data.Repository
import com.esa.foodrecipes.data.Resource
import com.esa.foodrecipes.data.response.CategoriesResponse
import com.esa.foodrecipes.data.response.CategoryAreaResponse
import com.esa.foodrecipes.data.response.FilterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _foodAreaCategories = MutableLiveData<CategoryAreaResponse>()
    val foodAreaCategories: LiveData<CategoryAreaResponse> get() = _foodAreaCategories


    private val _foodCategories = MutableLiveData<Resource<CategoriesResponse>>()
    val foodCategories: LiveData<Resource<CategoriesResponse>> get() = _foodCategories


    private val _foodListArea = MutableLiveData<Resource<FilterResponse>>()
    val foodListArea: LiveData<Resource<FilterResponse>> get() = _foodListArea

    fun fetchCategoriesArea() {
        viewModelScope.launch {
            _foodAreaCategories.value = repository.getAreaCategories()
        }
    }

    fun fetchFoodArea(area: String) {
        viewModelScope.launch {
            _foodListArea.value = Resource.Loading()
            _foodListArea.value = repository.getFoodArea(area)
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            _foodCategories.value = Resource.Loading()
            _foodCategories.value = repository.getCategories()
        }
    }
}