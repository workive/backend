package app.teamwize.api.assets.domain.model.response;


public record AssetCompactResponse(
        Long id,
        Long size,
        String contentType,
        String url
) {}