package app.teamwize.api.assets.domain.exception;

import app.teamwize.api.base.exception.ServerException;


public class AssetUploadFailedException extends ServerException {
    public AssetUploadFailedException(String message) {
        super(message);
    }
}
