package bt.org.dsp.crst.security;

import bt.org.dsp.crst.lib.CurrentUser;
import bt.org.dsp.crst.userManager.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private UserDetailServiceImpl userDetailServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailServiceImpl.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                CurrentUser currentUser = new CurrentUser();
//                currentUser.setEmployeeId(userDTO.getEmployeeId());
//                currentUser.setEmployeeCode(userDTO.getEmployeeCode());
//                currentUser.setEmail(userDTO.getEmail());
//                currentUser.setDepartmentId(userDTO.getDepartment().intValue());
//                currentUser.setDepartmentName(userDTO.getDepartmentName());
//                currentUser.setUserName(userDetails.getUsername());
//                currentUser.setFullName(userDTO.getFullName());
                currentUser.setServerDate(new Date());
                request.getSession().setAttribute("currentUser", currentUser);
            }
        }
        filterChain.doFilter(request, response);
    }
}
