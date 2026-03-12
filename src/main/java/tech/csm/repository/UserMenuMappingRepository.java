package tech.csm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tech.csm.model.UserMenuMapping;

import java.util.List;

@Repository
public interface UserMenuMappingRepository extends JpaRepository<UserMenuMapping, Long> {

    // ── Fetch all mappings for a user ────────────────────────────────────────
    List<UserMenuMapping> findByUserId(Long userId);

    // ── Fetch all mappings for a user that are enabled ───────────────────────
    List<UserMenuMapping> findByUserIdAndEnabledTrue(Long userId);

    // ── Fetch only the link IDs assigned to a user (enabled) ─────────────────
    @Query("SELECT m.menuLink.id FROM UserMenuMapping m WHERE m.user.id = :userId AND m.enabled = true")
    List<Long> findEnabledLinkIdsByUserId(@Param("userId") Long userId);

    // ── Fetch all users assigned to a specific link ───────────────────────────
    List<UserMenuMapping> findByMenuLinkId(Long menuLinkId);

    // ── Check if a specific link is already mapped to a user ─────────────────
    boolean existsByUserIdAndMenuLinkId(Long userId, Long menuLinkId);

    // ── Check if a specific enabled mapping exists ────────────────────────────
    boolean existsByUserIdAndMenuLinkIdAndEnabledTrue(Long userId, Long menuLinkId);

    // ── Count how many links are assigned to a user ───────────────────────────
    long countByUserIdAndEnabledTrue(Long userId);

    // ── Delete ALL mappings for a user (used before re-saving) ───────────────
    @Modifying
    @Query("DELETE FROM UserMenuMapping m WHERE m.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    // ── Delete a specific link mapping for a user ─────────────────────────────
    @Modifying
    @Query("DELETE FROM UserMenuMapping m WHERE m.user.id = :userId AND m.menuLink.id = :linkId")
    void deleteByUserIdAndMenuLinkId(@Param("userId") Long userId, @Param("linkId") Long linkId);

    // ── Soft-disable all mappings for a user ─────────────────────────────────
    @Modifying
    @Query("UPDATE UserMenuMapping m SET m.enabled = false WHERE m.user.id = :userId")
    void disableAllByUserId(@Param("userId") Long userId);

    // ── Fetch mappings for a user along with the link details ─────────────────
    @Query("SELECT m FROM UserMenuMapping m JOIN FETCH m.menuLink WHERE m.user.id = :userId AND m.enabled = true")
    List<UserMenuMapping> findEnabledMappingsWithLinks(@Param("userId") Long userId);
}
