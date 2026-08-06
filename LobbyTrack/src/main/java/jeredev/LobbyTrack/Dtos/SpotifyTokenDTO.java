package jeredev.LobbyTrack.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotifyTokenDTO(
        @JsonProperty("acces_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") int expiresIn
) {
}
