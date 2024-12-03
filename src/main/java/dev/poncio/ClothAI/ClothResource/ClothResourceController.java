package dev.poncio.ClothAI.ClothResource;

import dev.poncio.ClothAI.ClothResource.dto.ClothResourceDTO;
import dev.poncio.ClothAI.ClothResource.dto.CreateClothResourceDTO;
import dev.poncio.ClothAI.ClothResource.dto.UpdateClothResourceDTO;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/cloth-resource")
public class ClothResourceController {

    @Autowired
    private ClothResourceMapper mapper;

    @Autowired
    private ClothResourceService service;

    @Autowired
    private SecurityAccessControl securityAccessControl;

    @GetMapping(value = "/{companyId}/list")
    public List<ClothResourceDTO> listClothResources(@PathVariable Long companyId) {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.service.listClothResources(company).stream().map(this.mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/{companyId}/create", consumes = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ClothResourceDTO createClothResource(@PathVariable Long companyId, @RequestPart("body") CreateClothResourceDTO createClothResourceDTO, @RequestPart("attach") MultipartFile attach) throws IOException {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.mapper.toDto(this.service.createClothResource(company, createClothResourceDTO, attach));
    }

    @PatchMapping(value = "/{companyId}/update/{id}")
    public ClothResourceDTO updateClothResource(@PathVariable Long companyId, @PathVariable Long id, @RequestBody UpdateClothResourceDTO updateClothResourceDTO) {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.mapper.toDto(this.service.updateClothResource(id, updateClothResourceDTO));
    }

    @DeleteMapping("/{companyId}/delete/{id}")
    public ResponseEntity<Void> deleteClothResource(@PathVariable Long companyId, @PathVariable Long id) {
        this.securityAccessControl.checkAccessForCompany(companyId);
        this.service.deleteClothResource(id);
        return ResponseEntity.ok().build();
    }

}