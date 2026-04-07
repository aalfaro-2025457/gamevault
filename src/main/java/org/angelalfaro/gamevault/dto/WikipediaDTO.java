package org.angelalfaro.gamevault.dto;

import lombok.Data;

public record WikipediaDTO (
        String title,
        OriginalImage originalimage
){

    @Data
    public static class OriginalImage {
        private String source;
    }

}
