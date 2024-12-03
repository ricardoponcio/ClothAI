package dev.poncio.ClothAI.common.services;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Getter(AccessLevel.PROTECTED)
public abstract class CommonService {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private SecurityAccessControl securityAccessControl;

    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;

    @Autowired
    private S3StorageService s3StorageService;

}
