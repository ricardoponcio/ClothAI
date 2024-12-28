package dev.poncio.ClothAI.clothresource;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.clothresource.dto.ClothResourceDTO;
import dev.poncio.ClothAI.clothresource.dto.CreateClothResourceDTO;
import dev.poncio.ClothAI.clothresource.dto.UpdateClothResourceDTO;
import dev.poncio.ClothAI.common.controllers.CommonController;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cloth-resource")
public class ClothResourceController extends CommonController {

    @Autowired
    private ClothResourceMapper mapper;

    @Autowired
    private ClothResourceService service;

    @GetMapping(value = "/list")
    public List<ClothResourceDTO> listClothResources() {
        return this.service.listClothResources().stream().map(this.mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/create", consumes = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ClothResourceDTO createClothResource(@RequestPart("body") CreateClothResourceDTO createClothResourceDTO, @RequestPart("attach") MultipartFile attach) throws IOException {
        return this.mapper.toDto(this.service.createClothResource(createClothResourceDTO, attach));
    }

    @PatchMapping(value = "/update/{id}")
    public ClothResourceDTO updateClothResource(@PathVariable Long id, @RequestBody UpdateClothResourceDTO updateClothResourceDTO) {
        return this.mapper.toDto(this.service.updateClothResource(id, updateClothResourceDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClothResource(@PathVariable Long id) {
        this.service.deleteClothResource(id);
        return ResponseEntity.ok().build();
    }

}