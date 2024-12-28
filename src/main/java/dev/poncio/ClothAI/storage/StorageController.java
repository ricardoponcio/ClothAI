package dev.poncio.ClothAI.storage;

import dev.poncio.ClothAI.common.controllers.CommonController;
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
public class StorageController extends CommonController {

    @Autowired
    private StorageMapper mapper;

    @Autowired
    private StorageService service;

    @GetMapping(value = "/get")
    public StorageDTO getStorage() throws Exception {
        return this.mapper.toDto(this.service.get());
    }

    @PutMapping(value = "/create")
    public StorageDTO createStorage(@RequestBody CreateStorageDTO createStorageDTO) throws Exception {
        return this.mapper.toDto(this.service.createNewStorage(createStorageDTO));
    }

    @PatchMapping(value = "/update/{id}")
    public StorageDTO updateStorage(@PathVariable Long id, @RequestBody UpdateStorageDTO updateStorageDTO) {
        return this.mapper.toDto(this.service.updateStorage(id, updateStorageDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStorage(@PathVariable Long id) {
        this.service.deleteStorage(id);
        return ResponseEntity.ok().build();
    }

}