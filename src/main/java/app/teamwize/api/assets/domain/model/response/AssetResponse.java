package app.teamwize.api.assets.domain.model.response;

import app.teamwize.api.assets.domain.model.AssetCategory;

public record AssetResponse(
        Long id,
        Long size,
        String name,
        String contentType,
        String url,
        AssetCategory category
) {}