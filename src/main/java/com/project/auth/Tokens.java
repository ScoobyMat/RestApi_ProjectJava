package com.project.auth;

public class Tokens {
    private String accessToken;
    private String refreshToken;

    private Tokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokensBuilder builder() {
        return new TokensBuilder();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public static class TokensBuilder {
        private String accessToken;
        private String refreshToken;

        public TokensBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public TokensBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Tokens build() {
            return new Tokens(accessToken, refreshToken);
        }
    }
}
