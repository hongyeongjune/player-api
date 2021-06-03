package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdentity(String identity);
    List<UserEntity> findByNameAndBirth(String name, String birth);

    boolean existsByIdentity(String identity);
    boolean existsByIdentityAndNameAndBirth(String identity, String name, String birth);
}
