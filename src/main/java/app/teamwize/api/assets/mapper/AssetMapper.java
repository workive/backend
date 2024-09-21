package app.teamwize.api.assets.mapper;


import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.assets.domain.entity.Asset;
import app.teamwize.api.assets.domain.model.response.AssetResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = DefaultMapperConfig.class)
public interface AssetMapper {
    AssetResponse toAssetResponse(Asset fileAsset);

    List<AssetResponse> toAssetResponses(List<Asset> fileAsset);
}
