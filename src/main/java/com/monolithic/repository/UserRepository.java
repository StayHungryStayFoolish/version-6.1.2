package com.monolithic.repository;

import com.monolithic.domain.User;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String  USERS_BY_EMAIL_CACHE = "usersByEmail";

    String USERS_BY_PHONE_CACHE = "usersByPhone";

    String USERS_BY_APP_ID_CACHE = "usersByAppId";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByPhone(String phone);

    Optional<User> findOneByAppId(String appId);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_PHONE_CACHE)
    Optional<User> findOneWithAuthoritiesByPhone(String phone);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_APP_ID_CACHE)
    Optional<User> findOneWithAuthoritiesByAppId(String appId);

    Page<User> findAllByEmailNot(Pageable pageable, String email);
}
