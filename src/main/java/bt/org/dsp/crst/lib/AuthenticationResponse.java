package bt.org.dsp.crst.lib;

import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Set;

public class AuthenticationResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
    private String cid;
    private Set<GrantedAuthority> roles;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public AuthenticationResponse setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthenticationResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public AuthenticationResponse setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }
    public String getCid(){ return  cid;}

    public AuthenticationResponse setCid(String cid){
        this.cid =cid;
        return this;
    }
    public String getUsername() {
        return username;
    }

    public AuthenticationResponse setUsername(String username) {
        this.username = username;
        return this;
    }
    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public AuthenticationResponse setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
        return this;
    }
}
