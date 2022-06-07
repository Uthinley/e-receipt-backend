package bt.org.dsp.crst.userManager.controller;

import bt.org.dsp.crst.userManager.service.AuthService;
import bt.org.dsp.crst.lib.AuthenticationRequest;
import bt.org.dsp.crst.lib.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping (value = "/authenticate")
    public AuthenticationResponse authenticate (HttpServletRequest request, @RequestBody AuthenticationRequest authenticationRequest)throws Exception{
        return authService.authenticate(request, authenticationRequest);
    }
}
