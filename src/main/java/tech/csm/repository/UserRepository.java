package tech.csm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tech.csm.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ── Find by username (exact match) ────────────────────────────────────────
    Optional<User> findByUsername(String username);

    // ── Find by email (exact match) ───────────────────────────────────────────
    Optional<User> findByEmail(String email);

    // ── Find by username or email — used for login ────────────────────────────
    Optional<User> findByUsernameOrEmail(String username, String email);

    // ── Typeahead search: displayName OR username contains query ──────────────
    List<User> findByDisplayNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(
            String displayName, String username);

    // ── Check if username is already taken ────────────────────────────────────
    boolean existsByUsername(String username);

    // ── Check if email is already taken ──────────────────────────────────────
    boolean existsByEmail(String email);

    // ── Fetch all active users ────────────────────────────────────────────────
    List<User> findByActiveTrue();

    // ── Fetch all users with their menu mappings eagerly loaded ───────────────
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.menuMappings WHERE u.active = true")
    List<User> findAllActiveUsersWithMappings();

    // ── Find users who have a specific menu link assigned ─────────────────────
    @Query("""
            SELECT DISTINCT u FROM User u
            JOIN u.menuMappings m
            WHERE m.menuLink.id = :linkId
              AND m.enabled = true
           """)
    List<User> findUsersByAssignedLinkId(@Param("linkId") Long linkId);

    // ── Search active users only ──────────────────────────────────────────────
    @Query("""
            SELECT u FROM User u
            WHERE u.active = true
              AND (LOWER(u.displayName) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(u.username)    LIKE LOWER(CONCAT('%', :q, '%')))
           """)
    List<User> searchActiveUsers(@Param("q") String query);
}
