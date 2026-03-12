package tech.csm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tech.csm.model.MenuLink;

import java.util.List;

@Repository
public interface MenuLinkRepository extends JpaRepository<MenuLink, Long> {

    // ── Fetch all top-level (global) links — parent is null ──────────────────
    List<MenuLink> findByParentIsNull();

    // ── Fetch all top-level links ordered by name ────────────────────────────
    List<MenuLink> findByParentIsNullOrderByNameAsc();

    // ── Fetch all children of a given parent ─────────────────────────────────
    List<MenuLink> findByParentId(Long parentId);

    // ── Fetch children ordered by name ───────────────────────────────────────
    List<MenuLink> findByParentIdOrderByNameAsc(Long parentId);

    // ── Find by exact name (case-insensitive) ─────────────────────────────────
    List<MenuLink> findByNameIgnoreCase(String name);

    // ── Search links by name keyword ─────────────────────────────────────────
    List<MenuLink> findByNameContainingIgnoreCase(String keyword);

    // ── Fetch only global links (isGlobal = true) ────────────────────────────
    List<MenuLink> findByIsGlobalTrue();

    // ── Fetch only primary links (isGlobal = false) ──────────────────────────
    List<MenuLink> findByIsGlobalFalse();

    // ── Fetch all children under a specific parent ────────────────────────────
    @Query("SELECT m FROM MenuLink m WHERE m.parent.id = :parentId ORDER BY m.name ASC")
    List<MenuLink> findChildrenByParentId(Long parentId);

    // ── Fetch entire tree: all global links + their children eagerly ──────────
    @Query("SELECT DISTINCT m FROM MenuLink m LEFT JOIN FETCH m.children WHERE m.parent IS NULL ORDER BY m.name ASC")
    List<MenuLink> findAllWithChildren();

    // ── Check if a link name already exists under the same parent ─────────────
    boolean existsByNameIgnoreCaseAndParentId(String name, Long parentId);

    // ── Check if a top-level link name already exists ─────────────────────────
    boolean existsByNameIgnoreCaseAndParentIsNull(String name);
}