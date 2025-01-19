package app.teamwize.api.assets.domain.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class AssetNotFoundException extends NotFoundException {

    public AssetNotFoundException(String message) {
        super(message);
    }
}
