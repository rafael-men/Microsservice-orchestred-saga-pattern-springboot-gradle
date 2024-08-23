package br.com.microservices.orchestrated.orchestratorservice;

import lombok.Getter;

@Getter
public enum Etopics {

    START_SAGA("start-saga"),
    BASE_ORCHESTRATOR("orchestrator"),
    FINISH_SUCCESS("finish-success"),
    FINISH_FAIL("finish-fail"),
    PRODUCT_VALIDATION_SUCCESS("product-validation-success"),
    PRODUCT_VALIDATION_FAIL("product-validation-fail"),
    PAYMENT_SUCCESS("payment-success"),
    PAYMENT_FAIL("payment-validation-fail"),
    INVENTORY_SUCCESS("inventory-validation-success"),
    INVENTORY_FAIL("inventory-validation-fail"),
    NOTIFY_ENDING("notify-ending");

    private final String topic;

    // Construtor privado
    Etopics(String topic) {
        this.topic = topic;
    }
}
