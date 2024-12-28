package dev.poncio.ClothAI.common.services;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.common.interfaces.IEntityWithCompany;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Getter(AccessLevel.PROTECTED)
public abstract class CommonService<T extends IEntityWithCompany> {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private SecurityAccessControl securityAccessControl;

    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;

    @Autowired
    private S3StorageService s3StorageService;

    protected abstract T findById(Long id);

    protected T findByIdSecure(Long id) {
        final var entity = findById(id);
        this.checkSecurityEntity(entity);
        return entity;
    }

    protected final void checkSecurityEntity(T entity) {
        if (!entity.getCompany().getId().equals(this.getAuthContext().getManagedCompany().getId())) {
            throw new SecurityException("Entity not manageable by this company");
        }
    }

    protected final void checkSecurityEntityById(Long id) {
        checkSecurityEntity(findById(id));
    }

}
