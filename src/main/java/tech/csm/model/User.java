package tech.csm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an application user.
 *
 * MySQL table: users
 *
 * -- DDL ----------------------------------------------------------------
 * CREATE TABLE users (
 *   id            BIGINT        NOT NULL AUTO_INCREMENT,
 *   username      VARCHAR(100)  NOT NULL UNIQUE,
 *   display_name  VARCHAR(150)  NOT NULL,
 *   email         VARCHAR(150)  NOT NULL UNIQUE,
 *   password      VARCHAR(255)  NOT NULL,
 *   active        TINYINT(1)    NOT NULL DEFAULT 1,
 *   created_at    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
 *   updated_at    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
 *                                        ON UPDATE CURRENT_TIMESTAMP,
 *   PRIMARY KEY (id),
 *   UNIQUE KEY uk_username (username),
 *   UNIQUE KEY uk_email    (email)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 *
 * -- Seed data ----------------------------------------------------------
 * INSERT INTO users (id, username, display_name, email, password) VALUES
 *   (1, 'sheetal',  'Sheetal Agarwal (sheetal)', 'sheetal@raj.gov.in',  '$2a$10$hashedPassword1'),
 *   (2, 'ramesh',   'Ramesh Kumar (ramesh)',     'ramesh@raj.gov.in',   '$2a$10$hashedPassword2'),
 *   (3, 'priya',    'Priya Sharma (priya)',      'priya@raj.gov.in',    '$2a$10$hashedPassword3'),
 *   (4, 'amit',     'Amit Singh (amit)',         'amit@raj.gov.in',     '$2a$10$hashedPassword4'),
 *   (5, 'neha',     'Neha Gupta (neha)',         'neha@raj.gov.in',     '$2a$10$hashedPassword5');
 * -----------------------------------------------------------------------
 */
@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_email",    columnNames = "email")
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** Login identifier — e.g. "sheetal" */
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    /** Full display name shown in the UI — e.g. "Sheetal Agarwal (sheetal)" */
    @Column(name = "display_name", nullable = false, length = 150)
    private String displayName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    /** BCrypt-hashed password */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /** Soft-delete / account enable flag */
    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean active = true;

    /** All menu mappings assigned to this user */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserMenuMapping> menuMappings = new ArrayList<>();

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
    public User() {}

    public User(String username, String displayName, String email, String password) {
        this.username    = username;
        this.displayName = displayName;
        this.email       = email;
        this.password    = password;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getId()                   { return id; }
    public void setId(Long id)            { this.id = id; }

    public String getUsername()                  { return username; }
    public void setUsername(String username)     { this.username = username; }

    public String getDisplayName()               { return displayName; }
    public void setDisplayName(String name)      { this.displayName = name; }

    public String getEmail()                     { return email; }
    public void setEmail(String email)           { this.email = email; }

    public String getPassword()                  { return password; }
    public void setPassword(String password)     { this.password = password; }

    public boolean isActive()                    { return active; }
    public void setActive(boolean active)        { this.active = active; }

    public List<UserMenuMapping> getMenuMappings()                      { return menuMappings; }
    public void setMenuMappings(List<UserMenuMapping> menuMappings)     { this.menuMappings = menuMappings; }

    public LocalDateTime getCreatedAt()                  { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)    { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt()                  { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)    { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', displayName='" + displayName + "'}";
    }
}