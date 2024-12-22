package dev.poncio.ClothAI.aiexecution;

import dev.poncio.ClothAI.aiexecution.dto.StartClothAiTryOnExecutionDTO;
import dev.poncio.ClothAI.clothresource.ClothResourceService;
import dev.poncio.ClothAI.common.services.CommonService;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.TokenEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ClothAiTryOnExecutionService extends CommonService {

    @Autowired
    private IClothAiTryOnExecutionRepository repository;

    @Autowired
    private ClothAiTryOnExecutionMapper mapper;

    @Autowired
    private ClothResourceService clothResourceService;

    @Autowired
    @Qualifier("clothAiExecutionThreadPool")
    private TaskExecutor taskExecutor;

    public ClothAiTryOnExecutionEntity findByExecutionIdentification(String executionIdentification) {
        return this.repository.findByExecutionIdentification(executionIdentification).orElseThrow(EntityNotFoundException::new);
    }

    public List<ClothAiTryOnExecutionEntity> listWaitingExecution() {
        return this.repository.findAllByStartedAtIsNull();
    }

    public List<ClothAiTryOnExecutionEntity> listWaitingExecution(CompanyEntity company) {
        return this.repository.findAllByCompanyAndStartedAtIsNull(company);
    }

    public ClothAiTryOnExecutionEntity registerExecution(CompanyEntity company, TokenEntity token, StartClothAiTryOnExecutionDTO startClothAiTryOnExecutionDTO, MultipartFile attach) throws IOException {
        final var newExecution = new ClothAiTryOnExecutionEntity();
        newExecution.setCreatedAt(ZonedDateTime.now());
        newExecution.setExecutionIdentification(UUID.randomUUID().toString());
        newExecution.setCompany(company);
        newExecution.setToken(token);
        newExecution.setClothResource(this.clothResourceService.findByClothResourceIdentification(startClothAiTryOnExecutionDTO.getClothResourceIdentification()));
        final var uploadResponse = super.getS3StorageService().uploadFile(company, "execution/" + newExecution.getExecutionIdentification(), attach.getInputStream());
        newExecution.setInputUrl(uploadResponse);
        return this.repository.save(newExecution);
    }

    public ClothAiTryOnExecutionEntity startExecution(String executionIdentification) {
        final var savedExecution = this.findByExecutionIdentification(executionIdentification);
        savedExecution.setStartedAt(ZonedDateTime.now());
        return this.repository.save(savedExecution);
    }

    public ClothAiTryOnExecutionEntity successCompleteExecution(String executionIdentification, String messageResult, String outputUrl) {
        return this.updateExecution(executionIdentification, true, messageResult, outputUrl);
    }

    public ClothAiTryOnExecutionEntity errorCompleteExecution(String executionIdentification, String messageResult) {
        return this.updateExecution(executionIdentification, false, messageResult, null);
    }

    private ClothAiTryOnExecutionEntity updateExecution(String executionIdentification, boolean executionSuccess, String messageResult, String outputUrl) {
        final var savedExecution = this.findByExecutionIdentification(executionIdentification);
        savedExecution.setFinishedAt(ZonedDateTime.now());
        savedExecution.setStatusResult(executionSuccess);
        savedExecution.setMessageResult(messageResult);
        savedExecution.setOutputUrl(outputUrl);
        return this.repository.save(savedExecution);
    }

    @Scheduled(fixedRate = 60000)
    private synchronized void startWaitingExecutions() {
        this.repository.findAllByStartedAtIsNull().stream().forEach(execution -> {
            this.taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        startExecution(execution.getExecutionIdentification());
                        log.info("Executing clothai" + execution.getExecutionIdentification());
                        Thread.sleep(20000);
                        successCompleteExecution(execution.getExecutionIdentification(), "Finished", "outputUrlIdkWhereIs");
                    } catch (Exception ex) {
                        errorCompleteExecution(execution.getExecutionIdentification(), ex.getMessage());
                    }
                }
            });
        });
    }

}
