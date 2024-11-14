package com.shopeazy.config;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	//NOTE*-> IS BASICALLY GENERATING NEW TOKEN
	public String generateToken(Authentication auth) {
		String jwt = Jwts.builder()
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime()+846000000))
					.claim("email", auth.getName())
					.signWith(key).compact();
		return jwt;
	}

	/* NOTE*-> BELOW METHOD IS TO GET EMAIL FROM THE TOKEN[GENERATED IN THE BAOVE
	 * METHOD]. THE BELOW MRTHO WILL ACCEPT TOEKN AND WILL RETURN AN EMAIL*/
	public String getEmailFromToken(String jwt) {
		jwt = jwt.substring(7);// SINCE THE TOKEN IS WITH THE BAERERE[HAS 7 CHARECHTERS] KEY WORD, TO EXTRACT THIS KEY WORD WE ARE PASSING 7
		
		
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claims.get("email"));			  
		
		return email;
		
	}
	public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
