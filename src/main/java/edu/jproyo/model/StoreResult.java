package edu.jproyo.model;

/**
 * Result Wrapper of Storing Transaction
 */
public enum StoreResult {
    /**
     * Ok store result.
     */
    OK(201),
    /**
     * Old store result.
     */
    OLD(204),
    /**
     * Future store result.
     */
    FUTURE(422),
    /**
     * No content store result.
     */
    NO_CONTENT(204);

    private Integer status;

    StoreResult(Integer status){
        this.status = status;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }
}
