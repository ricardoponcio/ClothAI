package dev.poncio.ClothAI.aiexecution;

import dev.poncio.ClothAI.aiexecution.dto.ClothAiTryOnExecutionDTO;
import dev.poncio.ClothAI.aiexecution.dto.StartClothAiTryOnExecutionDTO;
import dev.poncio.ClothAI.common.annotations.OnlyM2MEndpoint;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer/api/cloth-ai-try-on-execution")
@OnlyM2MEndpoint
public class ClothAiTryOnExecutionController {

    @Autowired
    private ClothAiTryOnExecutionMapper mapper;

    @Autowired
    private ClothAiTryOnExecutionService service;

    @Autowired
    private SecurityAccessControl securityAccessControl;

    @GetMapping(value = "/{companyId}/list-waiting")
    public List<ClothAiTryOnExecutionDTO> listWaitingExecutions(@PathVariable Long companyId) {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.service.listWaitingExecution(company).stream().map(this.mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/{companyId}/register", consumes = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ClothAiTryOnExecutionDTO registerNewExecution(@PathVariable Long companyId, @RequestPart("body") StartClothAiTryOnExecutionDTO startClothAiTryOnExecutionDTO, @RequestPart("attach") MultipartFile attach) throws IOException {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.mapper.toDto(this.service.registerExecution(company, null, startClothAiTryOnExecutionDTO, attach));
    }

    @PatchMapping(value = "/{companyId}/details/{executionId}")
    public ClothAiTryOnExecutionDTO checkExecutionDetails(@PathVariable Long companyId, @PathVariable String executionId) {
        this.securityAccessControl.checkAccessForCompany(companyId);
        CompanyEntity company = CompanyEntity.builder().id(companyId).build();
        return this.mapper.toDto(this.service.findByExecutionIdentification(executionId));
    }

}