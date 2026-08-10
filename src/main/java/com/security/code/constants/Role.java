package com.security.code.constants;

import java.util.Set;

public enum Role {
    ROLE_ADMIN(Set.of(Permission.ROOM_ADD,Permission.ROOM_VIEW,Permission.ROOM_VIEW_ALL)),
    ROLE_CUSTOMER(Set.of(Permission.ROOM_VIEW)),
    ROLE_STAFF(Set.of(Permission.ROOM_VIEW,Permission.ROOM_VIEW_ALL));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }
}
