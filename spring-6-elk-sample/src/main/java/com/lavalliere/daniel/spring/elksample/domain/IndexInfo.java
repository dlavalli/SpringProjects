package com.lavalliere.daniel.spring.elksample.domain;

import lombok.Builder;

@Builder
public record IndexInfo(String name, String mappingPath, String settingPath) {
}
