package kr.co.player.api.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import kr.co.player.api.domain.user.model.common.UserRole;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.error.exception.UnauthorizedException;
import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import kr.co.player.api.infrastructure.error.exception.jwt.JwtTokenExpiredException;
import kr.co.player.api.infrastructure.error.exception.jwt.JwtTokenInvalidException;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret")
    private String SECRET_KEY;

    final String USER = "userId";
    private final long ACCESS_EXPIRE = 1000 * 60 * 30;
    private final long REFRESH_EXPIRE = 1000 * 60 * 60 * 24 * 14;

    private final UserRepository userRepository;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * 사용자 정보를 통해 Claims 객체를 만들어서 반환하는 메서드
     * @param identity 사용자 아이디
     * @param role 사용자 권한
     * @param name 사용자 이름
     * @return 사용자 정보를 포함한 Claims 객체
     */
    private Claims generateClaims(String identity, UserRole role, String name){
        Claims claims = Jwts.claims();
        claims.put(USER, identity);
        claims.put("role", role);
        claims.put("name",name);

        return claims;
    }

    /**
     * 사용자 정보를 통해 AccessToken 을 만드는 메서드
     * @param identity 사용자 아이디
     * @param role 사용자 권한
     * @param name 사용자 이름
     * @return 사용자의 AccessToken
     */
    public String createAccessToken(String identity, UserRole role, String name){
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + ACCESS_EXPIRE);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(generateClaims(identity,role,name))
                .setIssuedAt(issueDate)
                .setSubject("AccessToken")
                .setExpiration(expireDate) //유효시간 30분
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    /**
     * RefreshToken 을 이용하여 AccessToken 을 만들어내는 메서드
     * @param refreshToken 사용자의 RefreshToken
     * @return 사용자의 새로운 AccessToken
     */
    public String createAccessToken(String refreshToken){
        UserEntity userEntity = findUserByToken(refreshToken);

        if(!userEntity.getRefreshToken().equals(refreshToken))
            throw new UnauthorizedException();

        return createAccessToken(userEntity.getIdentity(), userEntity.getRole(), userEntity.getName());
    }

    /**
     * 사용자 정보를 통해 RefreshToken 을 만드는 메서드
     * @param identity 사용자 아이디
     * @param role 사용자 권한
     * @param name 사용자 이름
     * @return 사용자의 RefreshToken
     */
    public String createRefreshToken(String identity, UserRole role,String name){
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + REFRESH_EXPIRE);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(generateClaims(identity, role,name))
                .setIssuedAt(issueDate)
                .setSubject("RefreshToken")
                .setExpiration(expireDate) //유효시간 이주일
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    /**
     * 키 변환을 위한 key 를 만드는 메서드
     * @return secret key
     */
    private byte[] generateKey(){
        try{
            return SECRET_KEY.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new UserDefineException("키 변환에 실패하였습니다. ", e.getMessage());
        }
    }

    /**
     * 토큰의 유효성을 판단하는 메서드
     * @param token 토큰
     * @return 토큰이 만료되었는지에 대한 불리언 값
     * @exception ExpiredJwtException 토큰이 만료되었을 경우에 발생하는 예외
     */
    public boolean isUsable(String token){
        try{
            Jwts.parser()
                    .setSigningKey(generateKey())
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException e) {
            log.error("Invalid JWT signature");
            throw new JwtTokenInvalidException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
            throw new JwtTokenInvalidException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
            throw new JwtTokenExpiredException();
        } catch (IllegalArgumentException e) {
            log.error("Empty JWT claims");
            throw new JwtTokenInvalidException("Empty JWT claims");
        }
    }

    /**
     * 헤더에 있는 토큰을 추출하는 메서드
     * 평소에는 AccessToken을 담아서 주고 받다가 만료가 되었다는 예외가 발생하면 그때 Refresh만
     * @param request 사용자의 요청
     * @return AccessToken 과 RefreshToken 을 담은 객체를 Optional로 감싼 데이터
     */
    public Optional<String> resolveToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("Authorization"));
    }

    /**
     * 토큰을 이용하여 사용자 아이디를 찾는 메서드
     * @param token 토큰
     * @return 사용자의 아이디
     */
    public String findIdentityByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get(USER);
    }

    /**
     * 토큰을 이용하여 사용자 아이디를 찾는 메서드
     * @param token 토큰
     * @return 사용자의 아이디
     */
    public String findRoleByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

    /**
     * 토큰을 통해 UserEntity 객체를 가져오는 메서드
     * @param token : 토큰
     * @return : jwt 토큰을 통해 찾은 UserEntity 객체
     * @Exception UserNotFoundException : 해당 회원을 찾을 수 없는 경우 발생하는 예외
     */
    public UserEntity findUserByToken(String token){
        return userRepository.findByIdentity(findIdentityByToken(token))
                .orElseThrow(() -> new NotFoundException("UserEntity"));
    }

    /**
     * 토큰을 통해서 Authentication 객체를 만들어내는 메서드
     * @param token 토큰
     * @return 사용자 정보를 담은 UsernamePasswordAuthenticationToken 객체
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails =
                new User(findIdentityByToken(token),
                        "",
                        getAuthorities(UserRole.of(findRoleByToken(token))));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 권한을 읽어서 해당 유저의 권한이 무엇이 인지 Set에 저장하는 메서드
     * @param role 권한
     * @return 권한 정보를 담은 Set 객체
     */
    private Set<? extends GrantedAuthority> getAuthorities(UserRole role) {
        Set<GrantedAuthority> set = new HashSet<>();

        if(role.equals(UserRole.ADMIN)){
            set.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        set.add(new SimpleGrantedAuthority("ROLE_USER"));

        return set;
    }
}
