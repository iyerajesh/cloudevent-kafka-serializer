package com.xylia.platform.events.builder;

/**
 * Data content types, that the platform supports
 *
 * @author rajeshiyer
 */
public enum ApplyDataContentTypes {

    HIPED_ENCRYPTED_CONTENT("application/hiped-encrypted+json"),
    NO_ENCRYPTION("application/json");

    private String contentType;

    ApplyDataContentTypes(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
