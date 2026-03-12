package tech.csm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a menu link in the application.
 *
 * Two levels:
 *  - Global Link  → top-level entry (parent IS NULL,  is_global = 1)
 *  - Primary Link → child entry     (parent_id = x,   is_global = 0)
 *
 * MySQL table: menu_links
 *
 * -- DDL ----------------------------------------------------------------
 * CREATE TABLE menu_links (
 *   id          BIGINT       NOT NULL AUTO_INCREMENT,
 *   name        VARCHAR(150) NOT NULL,
 *   icon        VARCHAR(100)     NULL,
 *   route       VARCHAR(255)     NULL,
 *   is_global   TINYINT(1)   NOT NULL DEFAULT 0,
 *   parent_id   BIGINT           NULL,
 *   active      TINYINT(1)   NOT NULL DEFAULT 1,
 *   created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
 *   updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
 *                                      ON UPDATE CURRENT_TIMESTAMP,
 *   PRIMARY KEY (id),
 *   CONSTRAINT fk_menu_parent FOREIGN KEY (parent_id)
 *       REFERENCES menu_links (id) ON DELETE SET NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 *
 * -- Seed data (Global links) -------------------------------------------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (1,  'User Management',    '/user-management',    1, NULL),
 *   (2,  'Master',             '/master',             1, NULL),
 *   (3,  'Vendor Enrollment',  '/vendor-enrollment',  1, NULL),
 *   (4,  'Device Tagging',     '/device-tagging',     1, NULL),
 *   (5,  'Geo-Fencing',        '/geo-fencing',        1, NULL),
 *   (6,  'Configuration',      '/configuration',      1, NULL),
 *   (7,  'Reports',            '/reports',            1, NULL),
 *   (8,  'Challan Management', '/challan-management', 1, NULL),
 *   (9,  'Vehicle Tracking',   '/vehicle-tracking',   1, NULL),
 *   (10, 'Dashboard',          '/dashboard',          1, NULL);
 *
 * -- Seed data (Primary links — User Management, parent_id = 1) ----------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (101, 'View Users',        '/user-management/view',          0, 1),
 *   (102, 'Add User',          '/user-management/add',           0, 1),
 *   (103, 'Edit User',         '/user-management/edit',          0, 1),
 *   (104, 'Delete User',       '/user-management/delete',        0, 1),
 *   (105, 'Role Assignment',   '/user-management/roles',         0, 1),
 *   (106, 'Reset Password',    '/user-management/reset-password',0, 1),
 *   (107, 'User Activity Log', '/user-management/activity-log',  0, 1);
 *
 * -- Seed data (Primary links — Master, parent_id = 2) -------------------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (201, 'State Master',        '/master/state',        0, 2),
 *   (202, 'District Master',     '/master/district',     0, 2),
 *   (203, 'Mine Type Master',    '/master/mine-type',    0, 2),
 *   (204, 'Mineral Master',      '/master/mineral',      0, 2),
 *   (205, 'Vehicle Type Master', '/master/vehicle-type', 0, 2),
 *   (206, 'Route Master',        '/master/route',        0, 2),
 *   (207, 'Checkpoint Master',   '/master/checkpoint',   0, 2),
 *   (208, 'Holiday Master',      '/master/holiday',      0, 2);
 *
 * -- Seed data (Primary links — Vendor Enrollment, parent_id = 3) --------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (301, 'View Vendor Enrollment', '/vendor-enrollment/view',     0, 3),
 *   (302, 'Add Vendor',             '/vendor-enrollment/add',      0, 3),
 *   (303, 'Edit Vendor',            '/vendor-enrollment/edit',     0, 3),
 *   (304, 'Vendor Approval',        '/vendor-enrollment/approval', 0, 3),
 *   (305, 'Vendor Documents',       '/vendor-enrollment/docs',     0, 3),
 *   (306, 'Vendor Payment History', '/vendor-enrollment/payments', 0, 3),
 *   (307, 'Blacklist Vendor',       '/vendor-enrollment/blacklist',0, 3),
 *   (308, 'Vendor Reports',         '/vendor-enrollment/reports',  0, 3);
 *
 * -- Seed data (Primary links — Device Tagging, parent_id = 4) -----------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (401, 'View Tagged Devices',  '/device-tagging/view',        0, 4),
 *   (402, 'Tag New Device',       '/device-tagging/add',         0, 4),
 *   (403, 'Device Health Status', '/device-tagging/health',      0, 4),
 *   (404, 'RFID Management',      '/device-tagging/rfid',        0, 4),
 *   (405, 'GPS Device Mapping',   '/device-tagging/gps-mapping', 0, 4),
 *   (406, 'Device Replacement',   '/device-tagging/replacement', 0, 4),
 *   (407, 'Tamper Alerts',        '/device-tagging/tamper',      0, 4);
 *
 * -- Seed data (Primary links — Geo-Fencing, parent_id = 5) --------------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (501, 'View Geo-Zones',        '/geo-fencing/view',        0, 5),
 *   (502, 'Create Zone',           '/geo-fencing/create',      0, 5),
 *   (503, 'Edit Zone',             '/geo-fencing/edit',        0, 5),
 *   (504, 'Zone Violation Alerts', '/geo-fencing/violations',  0, 5),
 *   (505, 'Zone-Vehicle Mapping',  '/geo-fencing/mapping',     0, 5),
 *   (506, 'Entry / Exit Logs',     '/geo-fencing/logs',        0, 5),
 *   (507, 'Zone Analytics',        '/geo-fencing/analytics',   0, 5),
 *   (508, 'Restricted Area Setup', '/geo-fencing/restricted',  0, 5);
 *
 * -- Seed data (Primary links — Configuration, parent_id = 6) ------------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (601, 'System Settings',       '/configuration/settings',     0, 6),
 *   (602, 'Alert Configuration',   '/configuration/alerts',       0, 6),
 *   (603, 'SMS / Email Templates', '/configuration/templates',    0, 6),
 *   (604, 'API Integration',       '/configuration/api',          0, 6),
 *   (605, 'Audit Trail Settings',  '/configuration/audit',        0, 6),
 *   (606, 'Backup & Restore',      '/configuration/backup',       0, 6),
 *   (607, 'License Management',    '/configuration/license',      0, 6);
 *
 * -- Seed data (Primary links — Reports, parent_id = 7) ------------------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (701, 'Vehicle Trip Report',     '/reports/trip',        0, 7),
 *   (702, 'Daily Movement Report',   '/reports/movement',    0, 7),
 *   (703, 'Violation Report',        '/reports/violation',   0, 7),
 *   (704, 'Vendor Summary Report',   '/reports/vendor',      0, 7),
 *   (705, 'Device Health Report',    '/reports/device',      0, 7),
 *   (706, 'Challan Report',          '/reports/challan',     0, 7),
 *   (707, 'Mineral Dispatch Report', '/reports/mineral',     0, 7),
 *   (708, 'MIS Report',              '/reports/mis',         0, 7),
 *   (709, 'Custom Report Builder',   '/reports/custom',      0, 7);
 *
 * -- Seed data (Primary links — Challan Management, parent_id = 8) -------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (801, 'View Challans',       '/challan-management/view',       0, 8),
 *   (802, 'Generate Challan',    '/challan-management/generate',   0, 8),
 *   (803, 'Challan Dispute',     '/challan-management/dispute',    0, 8),
 *   (804, 'Payment Collection',  '/challan-management/payment',    0, 8),
 *   (805, 'Challan Analytics',   '/challan-management/analytics',  0, 8),
 *   (806, 'Exemption Management','/challan-management/exemption',  0, 8);
 *
 * -- Seed data (Primary links — Vehicle Tracking, parent_id = 9) ---------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (901, 'Live Tracking Map',        '/vehicle-tracking/live',      0, 9),
 *   (902, 'Trip History Playback',    '/vehicle-tracking/history',   0, 9),
 *   (903, 'Speed Violation Alerts',   '/vehicle-tracking/speed',     0, 9),
 *   (904, 'Idle Time Analysis',       '/vehicle-tracking/idle',      0, 9),
 *   (905, 'Route Deviation Alerts',   '/vehicle-tracking/deviation', 0, 9),
 *   (906, 'Vehicle Status Dashboard', '/vehicle-tracking/status',    0, 9),
 *   (907, 'Driver Behaviour',         '/vehicle-tracking/driver',    0, 9);
 *
 * -- Seed data (Primary links — Dashboard, parent_id = 10) ---------------
 * INSERT INTO menu_links (id, name, route, is_global, parent_id) VALUES
 *   (1001, 'Overview Dashboard',   '/dashboard/overview',   0, 10),
 *   (1002, 'Analytics Dashboard',  '/dashboard/analytics',  0, 10),
 *   (1003, 'Alert Summary',        '/dashboard/alerts',     0, 10),
 *   (1004, 'KPI Metrics',          '/dashboard/kpi',        0, 10),
 *   (1005, 'Heatmap View',         '/dashboard/heatmap',    0, 10);
 * -----------------------------------------------------------------------
 */
@Entity
@Table(name = "menu_links")
public class MenuLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "route", length = 255)
    private String route;

    /**
     * true  → Global Link  (top-level, parent IS NULL)
     * false → Primary Link (child, has a parent)
     */
    @Column(name = "is_global", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isGlobal = false;

    /** Parent global link — NULL for top-level entries */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private MenuLink parent;

    /** Children (primary links) of this global link */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    private List<MenuLink> children = new ArrayList<>();

    /** Soft-delete / visibility flag */
    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean active = true;

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
    public MenuLink() {}

    public MenuLink(String name, String route, boolean isGlobal, MenuLink parent) {
        this.name     = name;
        this.route    = route;
        this.isGlobal = isGlobal;
        this.parent   = parent;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getId()                      { return id; }
    public void setId(Long id)               { this.id = id; }

    public String getName()                  { return name; }
    public void setName(String name)         { this.name = name; }

    public String getIcon()                  { return icon; }
    public void setIcon(String icon)         { this.icon = icon; }

    public String getRoute()                 { return route; }
    public void setRoute(String route)       { this.route = route; }

    public boolean isGlobal()               { return isGlobal; }
    public void setGlobal(boolean isGlobal) { this.isGlobal = isGlobal; }

    public MenuLink getParent()              { return parent; }
    public void setParent(MenuLink parent)   { this.parent = parent; }

    public List<MenuLink> getChildren()              { return children; }
    public void setChildren(List<MenuLink> children) { this.children = children; }

    public boolean isActive()               { return active; }
    public void setActive(boolean active)   { this.active = active; }

    public LocalDateTime getCreatedAt()                    { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)      { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt()                    { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)      { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "MenuLink{id=" + id + ", name='" + name + "', isGlobal=" + isGlobal + "}";
    }
}