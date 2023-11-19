package com.esa.foodrecipes.data.response

import com.google.gson.annotations.SerializedName

data class CategoryAreaResponse(

    @field:SerializedName("meals")
    val meals: List<AreaItem?>? = null
)

data class AreaItem(

    @field:SerializedName("strArea")
    val strArea: String? = null
)
