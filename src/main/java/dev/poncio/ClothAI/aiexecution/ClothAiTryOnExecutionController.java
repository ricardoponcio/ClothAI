package dev.poncio.ClothAI.aiexecution;

import dev.poncio.ClothAI.aiexecution.dto.ClothAiTryOnExecutionDTO;
import dev.poncio.ClothAI.aiexecution.dto.StartClothAiTryOnExecutionDTO;
import dev.poncio.ClothAI.common.annotations.OnlyM2MEndpoint;
import dev.poncio.ClothAI.common.controllers.CommonController;
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
public class ClothAiTryOnExecutionController extends CommonController {

    @Autowired
    private ClothAiTryOnExecutionMapper mapper;

    @Autowired
    private ClothAiTryOnExecutionService service;

    @GetMapping(value = "/list-waiting")
    public List<ClothAiTryOnExecutionDTO> listWaitingExecutions() {
        return this.service.listWaitingExecutionByCompany().stream().map(this.mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/register", consumes = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ClothAiTryOnExecutionDTO registerNewExecution(@RequestPart("body") StartClothAiTryOnExecutionDTO startClothAiTryOnExecutionDTO, @RequestPart("attach") MultipartFile attach) throws IOException {
        return this.mapper.toDto(this.service.registerExecution(super.getAuthContext().getTokenEntityFromM2MRequest(), startClothAiTryOnExecutionDTO, attach));
    }

    @GetMapping(value = "/details/{executionId}")
    public ClothAiTryOnExecutionDTO checkExecutionDetails(@PathVariable String executionId) {
        return this.mapper.toDto(this.service.findByExecutionIdentification(executionId));
    }

}