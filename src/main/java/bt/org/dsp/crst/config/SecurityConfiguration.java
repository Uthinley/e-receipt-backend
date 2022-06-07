package bt.org.dsp.crst.config;

import bt.org.dsp.crst.security.JwtRequestFilter;
import bt.org.dsp.crst.userManager.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailServiceImpl userDetailServiceImpl;
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/refresh-token").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/forgot_password").permitAll()
                .antMatchers("/master/**").permitAll()
                .antMatchers("/trainer/**").permitAll()
                .antMatchers("/swagger-ui-custom.html","/swagger-ui/**","/v3/api-docs/**").permitAll()
                .antMatchers("/course/**").permitAll()
                .antMatchers("/student/**").permitAll()
                .antMatchers("/reset_password").permitAll()
                .antMatchers("/bills-result/files/**").permitAll()
                .antMatchers(HttpMethod.GET, "/subscription/viewFile/**").permitAll()
                .antMatchers(HttpMethod.GET, "/bills/viewFile/**").permitAll()
                .antMatchers(HttpMethod.GET, "/bills/findAuctionedBills/**").permitAll()
                .antMatchers(HttpMethod.GET, "/bills/getSelectedBill/").permitAll()
                .antMatchers(HttpMethod.GET, "/bills/getSelectedBill/**").permitAll()
                .antMatchers(HttpMethod.GET, "/allocation/getAllocationResultSummary/**").permitAll()
                .antMatchers(HttpMethod.GET, "/bills/getBillsForExternal/**").permitAll()
                .antMatchers(HttpMethod.GET, "/allocation/getTotalFSPBidders/**").permitAll()
                .antMatchers(HttpMethod.GET, "/allocation/getTotalNonFSPBidders/**").permitAll()
                .antMatchers(HttpMethod.GET, "/bills-result/viewComp/**").permitAll()
                .anyRequest().authenticated()

//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImpl);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
