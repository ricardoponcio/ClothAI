package dev.poncio.ClothAI.company.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String createdAt;
    private String updated_at;

}
