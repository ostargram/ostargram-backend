//package shop.iamhyunjun.ostargram.security.jwt;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@Slf4j
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String REFRESH_HEADER = "Refresh";
//
//
//    private JwtTokenProvider jwtTokenProvider;
//
//
//    // Jwt Token Util
//    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String jwtAccessToken = resolveToken(request, AUTHORIZATION_HEADER);
//
//
//        // Access 토큰이 정상 작동 하는 경우
//        if (jwtAccessToken != null && jwtTokenProvider.validateToken(jwtAccessToken) == JwtTokenProvider.JwtCode.ACCESS) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(jwtAccessToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.info("set Authentication to security context for '{}', uri: {}", authentication.getName(), request.getRequestURI());
//        }
//
//        // Access 토큰이 만료된 경우
//        else if (jwtAccessToken != null && jwtTokenProvider.validateToken(jwtAccessToken) == JwtTokenProvider.JwtCode.EXPIRED) {
//            log.info("JWT 토큰이 만료되어, Refresh token 확인 작업을 진행합니다.");
//
//            String jwtRefreshToken = resolveToken(request, REFRESH_HEADER);
//
//            // 1. refresh token 이 있나 확인한다.
//            // 2. 있다면 Access 토큰을 재발급 해준다.
//            if (jwtRefreshToken != null && jwtTokenProvider.validateToken(jwtRefreshToken) == JwtTokenProvider.JwtCode.ACCESS) {
//
//                // Refresh Token 재발급 과정
//                String newJwtRefreshToken = jwtTokenProvider.reissueRefreshToken(jwtRefreshToken);
//
//                // 재발급이 이상없이 됬다면.
//                if (newJwtRefreshToken != null) {
//                    response.setHeader(REFRESH_HEADER, "Bearer-" + newJwtRefreshToken);
//
//                    // access token 생성
//                    Authentication authentication = jwtTokenProvider.getAuthentication(jwtRefreshToken);
//                    response.setHeader(AUTHORIZATION_HEADER, "Bearer-" + jwtTokenProvider.createAccessToken(authentication));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    log.info("reissue refresh Token & access Token");
//                }
//            }
//        } else {
//            log.info("no valid JWT token found, uri: {}", request.getRequestURI());
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//
//    // Header에서 Name -> Authorization 값을 가저옴
//    private String resolveToken(HttpServletRequest request, String header) {
//
//        String bearerToken = request.getHeader(header);
//
//
//        if (bearerToken != null && bearerToken.startsWith("Bearer-")) {
//            // Bearer- 제외 토큰 값만 리턴
//            return bearerToken.substring(7);
//        }
//
//        // Access 토큰이 없다면 null 리턴
//        return null;
//    }
//}
