package app.teamwize.api.assets.service;


import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.service.OrganizationService;
import app.teamwize.api.assets.domain.entity.Asset;
import app.teamwize.api.assets.domain.exception.AssetNotFoundException;
import app.teamwize.api.assets.domain.exception.AssetUploadFailedException;
import app.teamwize.api.assets.domain.model.AssetCategory;
import app.teamwize.api.assets.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetService {

    private final OrganizationService organizationService;
    private final StorageService storageService;
    private final AssetRepository assetRepository;
    @Value("${app.storage.temp-directory}")
    private String tempDirectory;

    @Value("${app.storage.base-preview-url}")
    private String storageBasePreviewUrl;

    @Value("${app.storage.bucket}")
    private String bucketName;

    public Asset getAsset(Long organizationId, Long fileAssetId) throws AssetNotFoundException {
        return assetRepository.findByOrganizationIdAndId(organizationId, fileAssetId)
                .orElseThrow(() -> new AssetNotFoundException(fileAssetId));

    }

    public Asset getAssetByName(Long organizationId, String name) throws AssetNotFoundException {
        return assetRepository.findByOrganizationIdAndName(organizationId, name)
                .orElseThrow(() -> new AssetNotFoundException(0L));
    }

    public List<Asset> getAssets(Long organizationId, List<Long> fileAssetIds) {
        return assetRepository.findByOrganizationIdAndIdIn(organizationId, fileAssetIds);
    }

    public InputStream getAssetContent(Long organizationId, Asset asset) throws AssetNotFoundException {
        return storageService.getObjectAsBytes(bucketName, asset.getPath() + asset.getName());
    }


    @Transactional
    public List<Asset> createAssets(Long organizationId, AssetCategory category, List<MultipartFile> files)
            throws AssetUploadFailedException, OrganizationNotFoundException {
        var organization = organizationService.getOrganization(organizationId);
        try {
            var results = new ArrayList<Asset>();
            for (var request : files) {
                var ext = getFileExtension(request.getOriginalFilename()).orElse("unknown");
                var path = buildObjectPath(organizationId, category);
                var name = UUID.randomUUID() + "." + ext;
                var file = new File(tempDirectory, name);
                request.transferTo(file);
                var result = storageService.putObject(bucketName, path + name, file, ObjectCannedACL.PUBLIC_READ);
                var url = storageBasePreviewUrl + "/" + category.getPath() + "/" + name;

                var asset = new Asset()
                        .setName(name)
                        .setPath(path)
                        .setOriginalName(request.getOriginalFilename())
                        .setCategory(category)
                        .setContentType(request.getContentType())
                        .setUrl(url)
                        .setSize(file.length())
                        .setOrganization(organization);

                asset = assetRepository.save(asset);
                results.add(asset);
            }
            return results;
        } catch (IOException ex) {
            log.error("Exception in uploading files to s3", ex);
            throw new AssetUploadFailedException();
        }
    }

    public Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private String buildObjectPath(Long organizationId, AssetCategory type) {
        return "%d/%s/".formatted(organizationId, type.getPath());
    }

}
