package org.ankur.jwt;

import java.io.UnsupportedEncodingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String jwt = Jwts.builder().setSubject("users/TzMUocMF4p").claim("name", "Robert Token Man")
				.claim("scope", "self groups/admins").signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
				.compact();

		System.out.println(jwt);

		Jws<Claims> claims = Jwts.parser().setSigningKey("secret".getBytes("UTF-8")).parseClaimsJws(jwt);
		String scope = (String) claims.getBody().get("scope");
		System.out.println(scope);
	}
}
