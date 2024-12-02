package dev.poncio.ClothAI.storage;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.storage.dto.CreateStorageDTO;
import dev.poncio.ClothAI.storage.dto.StorageDTO;
import dev.poncio.ClothAI.storage.dto.UpdateStorageDTO;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    @Autowired
    private StorageMapper mapper;

    @Autowired
    private StorageService service;

    @Autowired
    private SecurityAccessControl securityAccessControl;

    @GetMapping(value = "/{companyId}/get")
    public StorageDTO getStorage(@PathVariable Long companyId) throws Exception {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.mapper.toDto(this.service.get(company));
    }

    @PutMapping(value = "/{companyId}/create")
    public StorageDTO createStorage(@PathVariable Long companyId, @RequestBody CreateStorageDTO createStorageDTO) throws Exception {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.mapper.toDto(this.service.createNewStorage(company, createStorageDTO));
    }

    @PatchMapping(value = "/{companyId}/update/{id}")
    public StorageDTO updateStorage(@PathVariable Long companyId, @PathVariable Long id, @RequestBody UpdateStorageDTO updateStorageDTO) {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.mapper.toDto(this.service.updateStorage(id, updateStorageDTO));
    }

    @DeleteMapping("/{companyId}/delete/{id}")
    public ResponseEntity<Void> deleteStorage(@PathVariable Long companyId, @PathVariable Long id) {
        this.securityAccessControl.checkAccessForCompany(companyId);
        this.service.deleteStorage(id);
        return ResponseEntity.ok().build();
    }

}