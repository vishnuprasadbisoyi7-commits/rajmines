package tech.csm.model;



import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Join table that records which MenuLinks are assigned to which User.
 *
 * MySQL table: user_menu_mappings
 *
 * -- DDL ----------------------------------------------------------------
 * CREATE TABLE user_menu_mappings (
 *   id            BIGINT     NOT NULL AUTO_INCREMENT,
 *   user_id       BIGINT     NOT NULL,
 *   menu_link_id  BIGINT     NOT NULL,
 *   enabled       TINYINT(1) NOT NULL DEFAULT 1,
 *   created_at    DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
 *   updated_at    DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP
 *                                     ON UPDATE CURRENT_TIMESTAMP,
 *   PRIMARY KEY (id),
 *   UNIQUE KEY uk_user_link (user_id, menu_link_id),
 *   CONSTRAINT fk_umm_user      FOREIGN KEY (user_id)
 *       REFERENCES users      (id) ON DELETE CASCADE,
 *   CONSTRAINT fk_umm_menu_link FOREIGN KEY (menu_link_id)
 *       REFERENCES menu_links (id) ON DELETE CASCADE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 *
 * -- Seed data ----------------------------------------------------------
 * Sheetal (user_id=1): Vendor Enrollment (3) + View Vendor Enrollment (301)
 *
 * INSERT INTO user_menu_mappings (user_id, menu_link_id, enabled) VALUES
 *   -- Sheetal Agarwal  → Vendor Enrollment + View Vendor Enrollment
 *   (1,  3,   1),
 *   (1,  301, 1),
 *
 *   -- Ramesh Kumar     → User Management + all its primary links
 *   (2,  1,   1),
 *   (2,  101, 1),
 *   (2,  102, 1),
 *   (2,  103, 1),
 *   (2,  104, 1),
 *   (2,  105, 1),
 *   (2,  106, 1),
 *   (2,  107, 1),
 *
 *   -- Priya Sharma     → Reports + selected primary links
 *   (3,  7,   1),
 *   (3,  701, 1),
 *   (3,  702, 1),
 *   (3,  703, 1),
 *   (3,  708, 1),
 *
 *   -- Amit Singh       → Dashboard + all its primary links
 *   (4,  10,   1),
 *   (4,  1001, 1),
 *   (4,  1002, 1),
 *   (4,  1003, 1),
 *   (4,  1004, 1),
 *   (4,  1005, 1),
 *
 *   -- Neha Gupta       → Vehicle Tracking + selected primary links
 *   (5,  9,   1),
 *   (5,  901, 1),
 *   (5,  902, 1),
 *   (5,  905, 1);
 * -----------------------------------------------------------------------
 */
@Entity
@Table(
    name = "user_menu_mappings",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_user_link",
            columnNames = { "user_id", "menu_link_id" }
        )
    }
)
public class UserMenuMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** The user this mapping belongs to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_umm_user"))
    private User user;

    /** The menu link (global or primary) that is assigned */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_link_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_umm_menu_link"))
    private MenuLink menuLink;

    /**
     * true  → link is active / visible in sidebar
     * false → link is soft-removed (hidden without deleting the row)
     */
    @Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // ── Lifecycle ─────────────────────────────────────────────────────────────
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ── Constructors ──────────────────────────────────────────────────────────
    public UserMenuMapping() {}

    public UserMenuMapping(User user, MenuLink menuLink) {
        this.user     = user;
        this.menuLink = menuLink;
        this.enabled  = true;
    }

    public UserMenuMapping(User user, MenuLink menuLink, boolean enabled) {
        this.user     = user;
        this.menuLink = menuLink;
        this.enabled  = enabled;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getId()                   { return id; }
    public void setId(Long id)            { this.id = id; }

    public User getUser()                 { return user; }
    public void setUser(User user)        { this.user = user; }

    public MenuLink getMenuLink()                    { return menuLink; }
    public void setMenuLink(MenuLink menuLink)       { this.menuLink = menuLink; }

    public boolean isEnabled()            { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public LocalDateTime getCreatedAt()                 { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)   { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt()                 { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)   { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "UserMenuMapping{id=" + id
             + ", userId="     + (user     != null ? user.getId()     : null)
             + ", menuLinkId=" + (menuLink != null ? menuLink.getId() : null)
             + ", enabled="    + enabled + "}";
    }
}