package com.niko.prokat.Model.enums;

public enum OrderStatus {
    PROCESSING("В обработке"),
    ACCEPTED("Принят"),
    CANCELED("Отменён"),
    FINISHED("Завершён");

    private String status;

    OrderStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
