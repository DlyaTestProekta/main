package ru.pachan.main.model.auth.rolePermissionPermissionLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RolePermissionPermissionLevelId implements Serializable {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_level_id")
    private Long permissionLevelId;

}
