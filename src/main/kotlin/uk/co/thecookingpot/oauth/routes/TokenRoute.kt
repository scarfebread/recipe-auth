package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.config.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.oauth.model.TokenRequest
import uk.co.thecookingpot.oauth.service.TokenService
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.UUID

import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.jwk.RSAKey
import uk.co.thecookingpot.oauth.service.JwtService

fun Route.token(tokenService: TokenService) {
    post("/token") {
        // TODO check client secret
        val request = TokenRequest.fromParameters(call.receiveParameters())

        call.respond(
            tokenService.generateToken(request)
        )
    }
}