package com.motuen.identity_service.mapper;

import com.motuen.identity_service.dto.request.PermissionRequest;
import com.motuen.identity_service.dto.response.PermissionResponse;
import com.motuen.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
