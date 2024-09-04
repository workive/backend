package app.teamwize.api.assets.controller;


import app.teamwize.api.assets.domain.entity.Asset;
import app.teamwize.api.assets.mapper.AssetMapper;
import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.assets.domain.exception.AssetNotFoundException;
import app.teamwize.api.assets.domain.exception.AssetUploadFailedException;
import app.teamwize.api.assets.domain.model.AssetCategory;
import app.teamwize.api.assets.domain.model.response.AssetResponse;
import app.teamwize.api.assets.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetMapper assetMapper;
    private final AssetService assetService;
    private final SecurityService securityService;

    @PostMapping(consumes = {"multipart/form-data"})
    public List<AssetResponse> createAsset(@RequestParam AssetCategory bucket, @RequestParam("files") List<MultipartFile> files)
            throws AssetUploadFailedException, OrganizationNotFoundException {
        var organizationId = securityService.getUserOrganizationId();
        return buildResponses(assetService.createAssets(organizationId, bucket, files));
    }

    @GetMapping("{id}")
    public AssetResponse getAsset(@PathVariable Long id) throws AssetNotFoundException {
        var organizationId = securityService.getUserOrganizationId();
        return buildResponse(assetService.getAsset(organizationId, id));
    }

    @GetMapping("preview/{category}/{name}")
    public ResponseEntity<InputStreamResource> getFileAssetContent(@PathVariable String category, @PathVariable String name) throws AssetNotFoundException {
        var organizationId = securityService.getUserOrganizationId();
        var fileAsset = assetService.getAssetByName(organizationId, name);
        var fileContent = assetService.getAssetContent(organizationId, fileAsset);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileAsset.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + name + "\"")
                .body(new InputStreamResource(fileContent));
    }


    private AssetResponse buildResponse(Asset asset) {
        return assetMapper.toAssetResponse(asset);
    }

    private List<AssetResponse> buildResponses(List<Asset> assets) {
        return assetMapper.toAssetResponses(assets);
    }
}
