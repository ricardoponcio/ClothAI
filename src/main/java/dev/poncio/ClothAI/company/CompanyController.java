package dev.poncio.ClothAI.company;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.auth.jwt.utils.JwtUtils;
import dev.poncio.ClothAI.common.annotations.EnableRequestWithNoCompany;
import dev.poncio.ClothAI.company.dto.CompanyDTO;
import dev.poncio.ClothAI.company.dto.CreateCompanyDTO;
import dev.poncio.ClothAI.company.dto.UpdateCompanyDTO;
import dev.poncio.ClothAI.utils.CookieManager;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/company")
@EnableRequestWithNoCompany
public class CompanyController {

    @Autowired
    private CompanyMapper mapper;

    @Autowired
    private CompanyService service;

    @Autowired
    private AuthContext authContext;

    @Autowired
    private SecurityAccessControl securityAccessControl;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CookieManager cookieManager;

    @GetMapping(value = "/list")
    public List<CompanyDTO> listCompanies() {
        return this.service.listCompanies().stream().map(this.mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/create")
    public CompanyDTO createCompany(@RequestBody CreateCompanyDTO createCompanyDTO) {
        return this.mapper.toDto(this.service.createCompany(createCompanyDTO));
    }

    @PatchMapping(value = "/update/{id}")
    public CompanyDTO updateCompany(@PathVariable Long id, @RequestBody UpdateCompanyDTO updateCompanyDTO) {
        return this.mapper.toDto(this.service.updateCompany(id, updateCompanyDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        CompanyEntity company = this.authContext.getManagedCompany();
        if (company != null && company.getId().equals(id))
            throw new InvalidParameterException("You cannot remove a managed company");
        this.service.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/start-manage/{id}")
    public void startManagingCompany(@PathVariable Long id, HttpServletResponse response) {
        this.securityAccessControl.checkAccessForCompany(id);
        String jwt = jwtUtils.generateJwtTokenCompany(CompanyEntity.builder().id(id).build());
        this.cookieManager.addCompanyCookie(response, jwt);
    }

}