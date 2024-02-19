package com.myrepo.rentacar.services.implementation;


import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.repositories.RentalUserRepository;
import com.myrepo.rentacar.services.DateService;
import com.myrepo.rentacar.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;




@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${backoffice.jwt.secret_key}")
    private String secret;
    private static final int KEY_LEN = 64;

    private final RentalUserRepository rentalUserRepository;
    private final RentalUserServiceImpl rentalUserServiceImpl;
    private final DateService dateService;



    public String generateToken(UserDetails userDetails, Long apiKeyId) {

        RentalUser rentalUser = rentalUserRepository.findByEmail(userDetails.getUsername()).get();

        Long id = rentalUser.getId();

        Key secretKey = new SecretKeySpec(getSigninKey(), "HmacSHA512");

//        rentalUser.setLastLogin(dateService.getCurrentDateTime());
//        foxUserRepository.save(rentalUser);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .claim("id", id)
                .claim("role", rentalUserServiceImpl.getRoleOfUser(rentalUser))
                .claim("client", apiKeyId)
                .claim("role", rentalUserServiceImpl.getRoleOfUser(rentalUser))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }



        private byte[] getSigninKey() {
            byte[] key = secret.getBytes();
            byte[] result = new byte[KEY_LEN];
            int p = 0;
            while (p < key.length) {
                int l = Math.min(key.length, result.length - p);
                System.arraycopy(key, 0, result, p, l);
                p += l;
            }

            return result;
        }

        public String extractUserName(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
            final Claims claims = extractAllClaim(token);
            return claimsResolvers.apply(claims);
        }

        public Claims extractAllClaim(String token) {
            return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
        }

        public boolean isTokenValid(String token, UserDetails userDetails) {
            final String username = extractUserName(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        private boolean isTokenExpired(String token) {
            return extractClaim(token, Claims::getExpiration).before(new Date());
        }

        public String extractUserRole(String token) {

            return extractClaim(token, claims -> claims.get("role", String.class));

        }

}
