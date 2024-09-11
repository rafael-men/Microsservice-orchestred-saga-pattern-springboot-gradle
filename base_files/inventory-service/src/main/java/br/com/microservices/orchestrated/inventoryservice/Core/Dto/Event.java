package br.com.microservices.orchestrated.inventoryservice.Core.Dto;



import br.com.microservices.orchestrated.inventoryservice.Core.Enums.ESagaStatus;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Event {

    @Id
    private String id;
    private String transactionId;
    private String orderId;
    private Order payload;
    private LocalDateTime createdAt;
    private String source;
    private ESagaStatus status;
    private List<History> eventHistory;


    public Event() {
    }


    public Event(String id, String transactionId, String orderId, Order payload,
                 LocalDateTime createdAt, String source, String status, List<History> eventHistory) {
        this.id = id;
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.payload = payload;
        this.createdAt = createdAt;
        this.source = source;
        this.status = ESagaStatus.valueOf(status);
        this.eventHistory = eventHistory;
    }


    public String getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Order getPayload() {
        return payload;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getSource() {
        return source;
    }

    public ESagaStatus getStatus() {
        return status;
    }

    public List<History> getEventHistory() {
        return eventHistory;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setPayload(Order payload) {
        this.payload = payload;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setStatus(String status) {
        this.status = ESagaStatus.valueOf(status);
    }

    public void setEventHistory(List<History> eventHistory) {
        this.eventHistory = eventHistory;
    }

    public void addToHistory (History history) {
        if(eventHistory == null) {
            eventHistory = new ArrayList<>();
        }
        eventHistory.add(history);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String id;
        private String transactionId;
        private String orderId;
        private Order payload;
        private LocalDateTime createdAt;
        private String source;
        private String status;
        private List<History> eventHistory;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setPayload(Order payload) {
            this.payload = payload;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setSource(String source) {
            this.source = source;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setEventHistory(List<History> eventHistory) {
            this.eventHistory = eventHistory;
            return this;
        }

        public Event build() {
            return new Event(id, transactionId, orderId, payload, createdAt, source, status, eventHistory);
        }
    }
}
