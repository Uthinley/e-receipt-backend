package bt.org.dsp.crst.userManager.service;

import bt.org.dsp.crst.userManager.dto.UserDTO;
import bt.org.dsp.crst.userManager.model.PermissionSetup;
import bt.org.dsp.crst.userManager.repository.IPermissionRepository;
import bt.org.dsp.crst.userManager.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;
    private IPermissionRepository iPermissionRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = iUserRepository.findByUsername(username);
        if (Objects.isNull(userDTO)) {
            throw new UsernameNotFoundException("No user " +
                    "Found with username : " + username);
        }

        return new org.springframework.security
                .core.userdetails.User(
                userDTO.getUsername(), userDTO.getPassword(),
                true, true, true, true, getAuthorities(userDTO.getGroupId()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Integer id) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<PermissionSetup> permissionSetup = iPermissionRepository.findAllByUserGroupId(id);
        permissionSetup.forEach(permission -> {
            String access = permission.getUserGroup().getId() +"-" + permission.getScreen().getId() + "-";
            if (permission.isView()) {
                authorities.add(new SimpleGrantedAuthority(access + "VIEW"));
            }

            if (permission.isSave()) {
                authorities.add(new SimpleGrantedAuthority(access + "ADD"));
            }

            if (permission.isEdit()) {
                authorities.add(new SimpleGrantedAuthority(access + "EDIT"));
            }

            if (permission.isDelete()) {
                authorities.add(new SimpleGrantedAuthority(access + "DELETE"));
            }
        });
        return authorities;
    }
}
