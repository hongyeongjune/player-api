package kr.co.player.api.infrastructure.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@EnableJpaAuditing(auditorAwareRef = "defaultAuditorAware")
@Configuration
public class JpaAuditConfig {

    @Bean
    public AuditorAware<String> defaultAuditorAware(){
        return () -> Optional.of("default");
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
