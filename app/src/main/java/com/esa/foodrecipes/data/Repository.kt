package com.esa.foodrecipes.data

import com.esa.foodrecipes.data.remote.ApiService
import com.esa.foodrecipes.data.response.CategoriesResponse
import com.esa.foodrecipes.data.response.CategoryAreaResponse
import com.esa.foodrecipes.data.response.DetailResponse
import com.esa.foodrecipes.data.response.FilterResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun searchFood(query: String): Resource<FilterResponse> {
        return try {
            val response = apiService.searchFood(query)
            Resource.Success(response)
        } catch (e: Exception){
            Resource.Error("An error occurred", null)
        }
    }

    suspend fun getCategories(): Resource<CategoriesResponse> {
        return try {
            val response = apiService.getCategories()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("An error occurred", null)
        }
    }

    suspend fun getFoodFilter(category: String): Resource<FilterResponse> {
        return try {
            val response = apiService.getFoodFilter(category)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("An error occurred", null)
        }
    }
    suspend fun getFoodArea(area: String): Resource<FilterResponse> {
        return try {
            val response = apiService.getFoodArea(area)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("An error occurred", null)
        }
    }
    suspend fun getAreaCategories(): CategoryAreaResponse = apiService.getAreaCategories()
    suspend fun getDetail(id: String): DetailResponse = apiService.getDetail(id)

}