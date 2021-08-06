package uk.co.thecookingpot.oauth.service

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.util.*

class JwtService {
    private lateinit var jwk: RSAKey

    init {
        generateJwks()
    }

    private fun generateJwks() {
        jwk = RSAKeyGenerator(2048)
            .keyUse(KeyUse.SIGNATURE)
            .keyID(UUID.randomUUID().toString())
            .generate()
    }

    fun createIdToken(nonce: String): String {
        val signer: JWSSigner = RSASSASigner(jwk)
        val claimsSet = JWTClaimsSet.Builder()
            .subject("jscarfe")
            .issuer("http://localhost:8085/")
            .audience("recipe-application")
            .expirationTime(Date(Date().time + 60 * 1000))
            .issueTime(Date())
            .claim("nonce", nonce)
            .build()
        val signedJWT = SignedJWT(
            JWSHeader.Builder(JWSAlgorithm.RS256).keyID(jwk.keyID).build(),
            claimsSet
        )
        signedJWT.sign(signer)

        return signedJWT.serialize()
    }

    fun getPublicKey(): RSAKey {
        return jwk.toPublicJWK()
    }
}