package br.com.microservices.orchestrated.productvalidationservice.Core.Dto;


import br.com.microservices.orchestrated.productvalidationservice.Core.Enums.ESagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {

    private String source;
    private ESagaStatus status;
    private String message;
    private LocalDateTime createdAt;
}
