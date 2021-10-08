package uk.co.thecookingpot.oauth.routes.validation

import com.google.gson.annotations.SerializedName

class ValidationFailure(val error: String, @SerializedName("error_description") val errorDescription: String)