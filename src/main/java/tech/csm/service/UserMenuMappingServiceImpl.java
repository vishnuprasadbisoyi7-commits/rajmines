package tech.csm.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.csm.dto.MenuLinkDTO;
import tech.csm.dto.SaveMappingRequest;
import tech.csm.model.MenuLink;
import tech.csm.model.User;
import tech.csm.model.UserMenuMapping;
import tech.csm.repository.MenuLinkRepository;
import tech.csm.repository.UserMenuMappingRepository;
import tech.csm.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMenuMappingServiceImpl implements UserMenuMappingService {

    @Autowired
    private MenuLinkRepository menuLinkRepository;

    @Autowired
    private UserMenuMappingRepository userMenuMappingRepository;

    @Autowired
    private UserRepository userRepository;

    // ────────────────────────────────────────────────────────────────────────────
    // 1. GET ALL LINKS WITH CHECKED STATE  →  used by the mapping form
    // ────────────────────────────────────────────────────────────────────────────
    /**
     * Returns the full menu-link tree (global + primary links).
     * Each node carries a `checked = true/false` flag based on what
     * is already saved for this user — so the Angular form can
     * pre-tick the right checkboxes.
     *
     * Steps:
     *  a) Fetch all link IDs currently enabled for this user.
     *  b) Fetch every top-level (global) link from DB.
     *  c) Convert each to a DTO, recursively attaching children,
     *     marking `checked` where the ID is in the enabled set.
     */
    @Override
    public List<MenuLinkDTO> getMenuLinksForUser(Long userId) {

        // (a) IDs the user already has — put in a Set for O(1) lookup
        Set<Long> enabledIds = new HashSet<>(
                userMenuMappingRepository.findEnabledLinkIdsByUserId(userId)
        );

        // (b) All global (parent IS NULL) links
        List<MenuLink> globalLinks = menuLinkRepository.findByParentIsNullOrderByNameAsc();

        // (c) Map to DTOs — children are included recursively inside toDTO()
        return globalLinks.stream()
                .map(link -> toDTO(link, enabledIds))
                .collect(Collectors.toList());
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 2. GET ONLY ASSIGNED LINKS  →  used by the Sidebar on login
    // ────────────────────────────────────────────────────────────────────────────
    /**
     * Returns ONLY the links that are checked/assigned to a user.
     * This is what the sidebar renders — no unchecked noise.
     *
     * Steps:
     *  a) Fetch enabled link IDs for user.
     *  b) Fetch all global links.
     *  c) Keep only globals that are in the enabled set.
     *  d) For each kept global, keep only its children that are also enabled.
     */
    @Override
    public List<MenuLinkDTO> getAssignedLinksForUser(Long userId) {

        // (a)
        Set<Long> enabledIds = new HashSet<>(
                userMenuMappingRepository.findEnabledLinkIdsByUserId(userId)
        );

        // (b)
        List<MenuLink> globalLinks = menuLinkRepository.findByParentIsNullOrderByNameAsc();

        // (c) + (d)
        return globalLinks.stream()
                .filter(link -> enabledIds.contains(link.getId()))   // only assigned globals
                .map(link -> {
                    MenuLinkDTO dto = toDTO(link, enabledIds);

                    // strip out unchecked children
                    if (dto.getChildren() != null) {
                        dto.setChildren(
                                dto.getChildren().stream()
                                        .filter(MenuLinkDTO::isChecked)
                                        .collect(Collectors.toList())
                        );
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 3. SAVE MAPPINGS  →  called when admin clicks Save on the form
    // ────────────────────────────────────────────────────────────────────────────
    /**
     * Full-replace strategy:
     *  a) Validate user exists.
     *  b) Delete ALL existing mappings for that user.
     *  c) Re-insert a UserMenuMapping row for every selected ID.
     *
     * Both global AND primary link IDs arrive in selectedLinkIds —
     * the Angular component sends every checked checkbox ID.
     *
     * @param request  { userId: 1, selectedLinkIds: [3, 301, 302, 7, 701] }
     */
    @Override
    @Transactional
    public void saveMappings(SaveMappingRequest request) {

        // (a) Validate user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + request.getUserId()
                ));

        // (b) Wipe existing mappings for this user
        userMenuMappingRepository.deleteByUserId(request.getUserId());

        // (c) Insert new mappings
        List<Long> selectedIds = request.getSelectedLinkIds();

        if (selectedIds != null && !selectedIds.isEmpty()) {

            // Fetch all MenuLink entities in one query (avoid N+1)
            List<MenuLink> links = menuLinkRepository.findAllById(selectedIds);

            // Build mapping rows
            List<UserMenuMapping> mappings = links.stream()
                    .map(link -> new UserMenuMapping(user, link, true))
                    .collect(Collectors.toList());

            userMenuMappingRepository.saveAll(mappings);
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 4. COPY MAPPINGS  →  "Copy From" feature on the form
    // ────────────────────────────────────────────────────────────────────────────
    /**
     * Copies all enabled link IDs from `fromUserId` and saves them
     * as the mapping for `toUserId`.  Internally reuses saveMappings()
     * so the same full-replace logic applies.
     *
     * Steps:
     *  a) Validate both users exist.
     *  b) Fetch enabled link IDs of the source user.
     *  c) Build a SaveMappingRequest for the target user.
     *  d) Delegate to saveMappings().
     *
     * @param fromUserId  user whose mappings are the template
     * @param toUserId    user who receives the copied mappings
     */
    @Override
    @Transactional
    public void copyMappings(Long fromUserId, Long toUserId) {

        // (a) Validate both users exist
        userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Source user not found with id: " + fromUserId
                ));
        userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Target user not found with id: " + toUserId
                ));

        // (b) Fetch source user's enabled link IDs
        List<Long> sourceIds =
                userMenuMappingRepository.findEnabledLinkIdsByUserId(fromUserId);

        // (c) Build request for target user
        SaveMappingRequest request = new SaveMappingRequest();
        request.setUserId(toUserId);
        request.setSelectedLinkIds(sourceIds);

        // (d) Reuse save logic (also handles empty list gracefully)
        saveMappings(request);
    }

    // ────────────────────────────────────────────────────────────────────────────
    // 5. SEARCH USERS  →  "Copy From" typeahead / user search
    // ────────────────────────────────────────────────────────────────────────────
    /**
     * Returns a list of users whose displayName OR username contains
     * the search query (case-insensitive).
     *
     * Each result map contains:
     *   { "id", "username", "displayName", "email" }
     *
     * @param query  partial name or username to search
     */
    @Override
    public List<Map<String, Object>> searchUsers(String query) {

        // Guard: return empty list for blank queries
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return userRepository
                .findByDisplayNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                        query.trim(), query.trim()
                )
                .stream()
                .map(user -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id",          user.getId());
                    map.put("username",    user.getUsername());
                    map.put("displayName", user.getDisplayName());
                    map.put("email",       user.getEmail());
                    return map;
                })
                .collect(Collectors.toList());
    }

    // ────────────────────────────────────────────────────────────────────────────
    // PRIVATE HELPER  →  MenuLink entity → MenuLinkDTO (recursive)
    // ────────────────────────────────────────────────────────────────────────────
    /**
     * Converts a MenuLink entity (and its children) into a MenuLinkDTO.
     * The `checked` flag is set by looking the link's ID up in enabledIds.
     * Children are converted recursively, so the full tree is built in one pass.
     *
     * @param link       the MenuLink entity to convert
     * @param enabledIds set of all link IDs currently enabled for the user
     */
    private MenuLinkDTO toDTO(MenuLink link, Set<Long> enabledIds) {

        MenuLinkDTO dto = new MenuLinkDTO();
        dto.setId(link.getId());
        dto.setName(link.getName());
        dto.setIcon(link.getIcon());
        dto.setRoute(link.getRoute());
        dto.setGlobal(link.isGlobal());
        dto.setChecked(enabledIds.contains(link.getId()));

        // Recursively convert children (primary links)
        if (link.getChildren() != null && !link.getChildren().isEmpty()) {
            List<MenuLinkDTO> childDTOs = link.getChildren().stream()
                    .map(child -> toDTO(child, enabledIds))
                    .collect(Collectors.toList());
            dto.setChildren(childDTOs);
        } else {
            dto.setChildren(Collections.emptyList());
        }

        return dto;
    }
}