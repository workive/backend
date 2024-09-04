package app.teamwize.api.assets.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AssetCategory {
    PROFILE_IMAGE("profile_images"),;

    private final String path;
}
