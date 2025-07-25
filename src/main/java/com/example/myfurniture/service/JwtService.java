package com.example.myfurniture.service;

import com.example.myfurniture.dto.response.IntrospectResponse;
import com.example.myfurniture.entity.Account;
import com.example.myfurniture.entity.Permission;
import com.example.myfurniture.entity.Role;
import com.example.myfurniture.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.signerKey}")
    private String signerKey;

    public String genToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        List<String> authorities = new ArrayList<>();
        for (Role role : account.getRoles()) {
            authorities.add("ROLE_" + role.getName());
            for (Permission permission : role.getPermissions()) {
                authorities.add(permission.getName());
            }
        }

        String authoritiesStr = String.join(",", authorities);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("livora.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))

                .claim("authorities", authoritiesStr)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public String extractUsername(String token) throws ParseException, JOSEException{
        JWSObject jwsObject = JWSObject.parse(token);
        if(!jwsObject.verify(new MACVerifier(signerKey))){
            throw new JOSEException("Invalid token");
        }
        // Lấy claims từ payload của token
        JWTClaimsSet claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        return claims.getSubject();
    }

    public IntrospectResponse introspect(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey);
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(verifier);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return IntrospectResponse.builder().valid(verified&&expiryTime.after(new Date())).build();
    }

    // Phương thức kiểm tra token có hợp lệ không
    public boolean validateToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);

            // Xác thực chữ ký
            return jwsObject.verify(new MACVerifier(signerKey));
        } catch (Exception e) {
            return false;
        }
    }
}
