package uk.co.thecookingpot.oauth.model

import com.google.gson.annotations.SerializedName

class Token {
    @SerializedName("access_token")
    lateinit var accessToken: String

    @SerializedName("refresh_token")
    lateinit var refreshToken: String

    @SerializedName("token_type")
    lateinit var tokenType: String

    @SerializedName("id_token")
    lateinit var idToken: String
}