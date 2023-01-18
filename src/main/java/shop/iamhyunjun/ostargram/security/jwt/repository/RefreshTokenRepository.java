//package shop.iamhyunjun.ostargram.security.jwt.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import shop.iamhyunjun.ostargram.security.jwt.entity.RefreshToken;
//
//import java.util.Optional;
//
//public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
//
//    Optional<RefreshToken> findByUserId(String userId);
//}