package br.com.microservices.orchestrated.inventoryservice.Core.Dto;




import br.com.microservices.orchestrated.inventoryservice.Core.Enums.ESagaStatus;

import java.time.LocalDateTime;

public class History {

    private String source;
    private ESagaStatus status;
    private String message;
    private LocalDateTime createdAt;


    public History() {
    }


    public History(HistoryBuilder builder) {
        this.source = builder.source;
        this.status = builder.status;
        this.message = builder.message;
        this.createdAt = builder.createdAt;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ESagaStatus getStatus() {
        return status;
    }

    public void setStatus(ESagaStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static HistoryBuilder builder() {
        return new HistoryBuilder();
    }


    public static class HistoryBuilder {
        private String source;
        private ESagaStatus status;
        private String message;
        private LocalDateTime createdAt;


        public HistoryBuilder source(String source) {
            this.source = source;
            return this;
        }

        public HistoryBuilder status(ESagaStatus status) {
            this.status = status;
            return this;
        }

        public HistoryBuilder message(String message) {
            this.message = message;
            return this;
        }

        public HistoryBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public History build() {
            return new History(this);
        }
    }
}
