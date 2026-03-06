package tech.csm.keygen;

import java.util.Base64;
import io.jsonwebtoken.security.Keys;

public class JwtKeyGen {
    public static void main(String[] args) {
        byte[] key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded();
        System.out.println(Base64.getEncoder().encodeToString(key));
    }
}


// Use this file to generate the jwt key for one time and use this key in application.properties files this adjust this key to jwt utils file.