package app.teamwize.api.assets.domain.exception;

import app.teamwize.api.base.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@ResponseStatus(NOT_FOUND)
public class AssetNotFoundException extends NotFoundException {

    private final long fileAssetId;
}
