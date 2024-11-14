package com.shopeazy.config;

import java.io.IOException;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		if(jwt!=null) {
			//NOTE*-> REASON WHY WE GAVE 7 IS BEACAUSE OUR TOKEN STARTS AFTER Bearereshreeram AND TO EXTARCT Baerere KEYWORD[Bearere HAS 7 CHARECTER AND govindagovindajaimaatuljabhavanijaishreeramjaihanuman IS OUR TOKEN]
//			jwt = jwt.substring(7);
			if (jwt.startsWith("Bearer ")) {
			    jwt = jwt.substring(7);
			} else {
			    throw new BadCredentialsException("Invalid token format");
			}
			try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
			
				String email = String.valueOf(claims.get("email"));
				String authorities = String.valueOf(claims.get("authorities"));
				
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, auths);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				throw new BadCredentialsException("INVALID TOKEN... FROM JWT VALIDATOR");
			}
		}
		filterChain.doFilter(request, response);
		
	}
	
	
	
}
