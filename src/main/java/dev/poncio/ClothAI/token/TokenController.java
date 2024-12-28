package dev.poncio.ClothAI.token;

import dev.poncio.ClothAI.common.controllers.CommonController;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.dto.CreateTokenDTO;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import dev.poncio.ClothAI.token.dto.UpdateTokenDTO;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/token")
public class TokenController extends CommonController {

    @Autowired
    private TokenMapper mapper;

    @Autowired
    private TokenService service;

    @GetMapping(value = "/{companyId}/list")
    public List<TokenDTO> listTokens(@PathVariable Long companyId) throws Exception {
        return this.service.listTokens().stream().map(this.mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/{companyId}/create")
    public TokenDTO createCompany(@PathVariable Long companyId, @RequestBody CreateTokenDTO createTokenDTO) {
        return this.mapper.toDto(this.service.createNewToken(createTokenDTO));
    }

    @PatchMapping(value = "/{companyId}/update/{id}")
    public TokenDTO updateToken(@PathVariable Long companyId, @PathVariable Long id, @RequestBody UpdateTokenDTO updateTokenDTO) {
        return this.mapper.toDto(this.service.updateToken(id, updateTokenDTO));
    }

    @DeleteMapping("/{companyId}/delete/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable Long companyId, @PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

}