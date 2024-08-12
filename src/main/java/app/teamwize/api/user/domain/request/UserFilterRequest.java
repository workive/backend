package app.teamwize.api.user.domain.request;

public record UserFilterRequest(Long teamId,String searchTerm) {
}
