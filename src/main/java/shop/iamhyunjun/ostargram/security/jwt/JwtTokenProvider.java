//package shop.iamhyunjun.ostargram.security.jwt;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsServiceImpl;
//import shop.iamhyunjun.ostargram.security.jwt.entity.RefreshToken;
//import shop.iamhyunjun.ostargram.security.jwt.repository.RefreshTokenRepository;
//
//import javax.transaction.Transactional;
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class JwtTokenProvider implements InitializingBean {
//
//    private final UserDetailsServiceImpl userDetailsService;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final String secretKey;
//    private final long tokenValidityInMs;
//    private final long refreshTokenValidityInMs;
//
//
//    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
//                            @Value("${jwt.token-validity-in-sec}") long tokenValidity,
//                            @Value("${jwt.refresh-token-validity-in-sec}") long refreshTokenValidity,
//                            UserDetailsServiceImpl userDetailsService,
//                            RefreshTokenRepository refreshTokenRepository){
//        this.secretKey = secretKey;
//        this.tokenValidityInMs = tokenValidity * 1000;
//        this.refreshTokenValidityInMs = refreshTokenValidity * 1000;
//        this.userDetailsService = userDetailsService;
//        this.refreshTokenRepository = refreshTokenRepository;
//    }
//
//    private Key key;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {  // init()
//        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
//
//    }
//
//    public String createAccessToken(Authentication authentication) {
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + tokenValidityInMs);
//
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .setIssuedAt(now) // 발행시간
//                .signWith(key, SignatureAlgorithm.HS512) // 암호화
//                .setExpiration(validity) // 만료
//                .compact();
//    }
//
//    // Refresh Token을 검증 후 클라이언트의 정보를 가지고 온다.
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
//        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
//    }
//
//    // Access 토큰의 유효성을 검증한다.
//    public JwtCode validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            log.info("JWT 토큰 검증 완료");
//            return JwtCode.ACCESS;
//        } catch (ExpiredJwtException e){
//            // Access Token이 만료된 경우에는
//            // refresh token을 확인하기 위해 EXPIRED 리턴
//            log.info("JWT 토큰 만료됨");
//            return JwtCode.EXPIRED;
//        } catch (JwtException | IllegalArgumentException e) {
//            log.info("jwtException : {}", e);
//        }
//        return JwtCode.DENIED;
//    }
//
//    // Refresh Token 재발급
//    @Transactional
//    public String reissueRefreshToken(String refreshToken) throws RuntimeException{
//
//        // Refresh Token 검증 -> 해당 유저의 정보를 가지고 온다.
//        Authentication authentication = getAuthentication(refreshToken);
//
//        // 해당 유저 정보와 맞는 Refresh Token이 DB에 있는지 확인.
//        RefreshToken findRefreshToken = refreshTokenRepository.findByUserId(authentication.getName())
//                .orElseThrow(() -> new UsernameNotFoundException("userId : " + authentication.getName() + " was not found"));
//
//        // DB에 있다면 꺼낸 Refresh Token이 클라이언트가 요청한 Refresh Token과 같은지 한번 더 확인.
//        if(findRefreshToken.getToken().equals(refreshToken)){
//            // 새로운 RefreshToken 발급
//            String newRefreshToken = createRefreshToken(authentication);
//
//            // 새로운 RefreshToken 업데이트.
//            findRefreshToken.changeToken(newRefreshToken);
//            return newRefreshToken;
//        }
//        else {
//            log.info("refresh 토큰이 일치하지 않습니다. ");
//            return null;
//        }
//    }
//
//    @Transactional
//    public String issueRefreshToken(Authentication authentication){
//        String newRefreshToken = createRefreshToken(authentication);
//
//        // 기존것이 있다면 바꿔주고, 없다면 만들어줌
//        refreshTokenRepository.findByUserId(authentication.getName())
//                .ifPresentOrElse(
//                        r-> {r.changeToken(newRefreshToken);
//                            log.info("issueRefreshToken method | change token ");
//                        },
//                        () -> {
//                            RefreshToken token = RefreshToken.createToken(authentication.getName(), newRefreshToken);
//                            log.info(" issueRefreshToken method | save tokenID : {}, token : {}", token.getUserId(), token.getToken());
//                            refreshTokenRepository.save(token);
//                        });
//
//        return newRefreshToken;
//    }
//
//    private String createRefreshToken(Authentication authentication){
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + refreshTokenValidityInMs);
//
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .setIssuedAt(now)
//                .signWith(key, SignatureAlgorithm.HS512)
//                .setExpiration(validity)
//                .compact();
//    }
//
//    public static enum JwtCode{
//        DENIED,
//        ACCESS,
//        EXPIRED;
//    }
//}