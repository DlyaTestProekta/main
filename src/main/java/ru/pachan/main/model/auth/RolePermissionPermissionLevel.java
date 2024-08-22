package ru.pachan.main.model.auth;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles_permissions_permission_levels")
public class RolePermissionPermissionLevel {

    @EmbeddedId
    private RolePermissionPermissionLevelId id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @ManyToOne
    @MapsId("permissionLevelId")
    @JoinColumn(name = "permission_level_id")
    private PermissionLevel permissionLevel;


}