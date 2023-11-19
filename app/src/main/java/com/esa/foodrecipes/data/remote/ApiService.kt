package com.esa.foodrecipes.data.remote

import com.esa.foodrecipes.data.response.CategoriesResponse
import com.esa.foodrecipes.data.response.CategoryAreaResponse
import com.esa.foodrecipes.data.response.DetailResponse
import com.esa.foodrecipes.data.response.FilterResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("search.php")
    suspend fun searchFood(@Query("s") query: String): FilterResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("list.php?a=list")
    suspend fun getAreaCategories(): CategoryAreaResponse

    @GET("filter.php")
    suspend fun getFoodFilter(@Query("c") category: String): FilterResponse

    @GET("filter.php")
    suspend fun getFoodArea(@Query("a") area: String): FilterResponse

    @GET("lookup.php")
    suspend fun getDetail(@Query("i") itemId: String): DetailResponse

}