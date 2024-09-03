package br.com.microservices.orchestrated.orderservice.Core.Document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "event")
public class Event {

    @Id
    private String id;
    private String transactionId;
    private String orderId;
    private Order payload;
    private LocalDateTime createdAt;
    private String source;
    private String status;
    private List<History> eventHistory;

    // Construtor sem argumentos
    public Event() {
    }

    // Construtor com todos os argumentos
    public Event(String id, String transactionId, String orderId, Order payload,
                 LocalDateTime createdAt, String source, String status, List<History> eventHistory) {
        this.id = id;
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.payload = payload;
        this.createdAt = createdAt;
        this.source = source;
        this.status = status;
        this.eventHistory = eventHistory;
    }

    // Getters
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

    public String getStatus() {
        return status;
    }

    public List<History> getEventHistory() {
        return eventHistory;
    }

    // Setters
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
        this.status = status;
    }

    public void setEventHistory(List<History> eventHistory) {
        this.eventHistory = eventHistory;
    }


    public static Builder builder() {
        return new Builder();
    }

    // Padrão Builder
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
