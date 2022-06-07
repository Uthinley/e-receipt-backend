package bt.org.dsp.crst.userManager.service;

import bt.org.dsp.crst.lib.AuthenticationRequest;
import bt.org.dsp.crst.lib.AuthenticationResponse;
import bt.org.dsp.crst.lib.RefreshTokenRequest;
import bt.org.dsp.crst.security.JwtUtil;
import bt.org.dsp.crst.userManager.model.Users;
import bt.org.dsp.crst.userManager.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private RefreshTokenService refreshTokenService;
    private UserDetailServiceImpl userDetailServiceImpl;
    private final IUserRepository userRepository;

    public AuthenticationResponse authenticate(HttpServletRequest request, AuthenticationRequest authenticationRequest) throws  Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        }catch(BadCredentialsException ex){
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse()
                .setUsername(authenticationRequest.getUsername())
//                .setCid(authenticationRequest.getCid())
                .setAuthenticationToken(jwt)
                .setExpiresAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMillis()))
                .setRoles((Set<GrantedAuthority>) userDetails.getAuthorities());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtUtil.generateTokenWithUserName(refreshTokenRequest.getUserName());
        return new AuthenticationResponse()
                .setUsername(refreshTokenRequest.getUserName())
                .setAuthenticationToken(token)
                .setExpiresAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMillis()))
                .setRefreshToken(refreshTokenService.generateRefreshToken());
    }
    @Transactional(readOnly = true)
    public Users getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                        getContext().getAuthentication().getPrincipal();
        return userRepository.findByUser(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public Boolean isFirstLogin(String cid){
        Optional<Users> userList = userRepository.findByCid(cid);
        Users _userFound = userList.get();
        return _userFound.getIsFirstLogin();
    }
}
