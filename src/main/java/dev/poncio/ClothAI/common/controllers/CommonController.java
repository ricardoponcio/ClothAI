package dev.poncio.ClothAI.common.controllers;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.common.services.S3StorageService;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Getter(AccessLevel.PROTECTED)
public abstract class CommonController {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private SecurityAccessControl securityAccessControl;

}
