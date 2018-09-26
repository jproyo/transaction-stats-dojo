package com.n26.model;

public enum StoreResult {
    OK(201),
    OLD(204),
    FUTURE(422),
    NO_CONTENT(204);

    private Integer status;

    StoreResult(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
