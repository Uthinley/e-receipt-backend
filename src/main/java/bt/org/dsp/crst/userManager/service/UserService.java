package bt.org.dsp.crst.userManager.service;

import bt.org.dsp.crst.base.BaseService;
import bt.org.dsp.crst.lib.ResponseMessage;
import bt.org.dsp.crst.userManager.model.Users;
import bt.org.dsp.crst.userManager.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
public class UserService extends BaseService {
    private IUserRepository userRepository;
    private AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseMessage save(Users user) {
        if (StringUtils.isEmpty(user)) {
            responseMessage.setStatus(UNSUCCESSFUL_STATUS);
            responseMessage.setResponseText("Nothing to save.");
            return responseMessage;
        }
        try {
            Optional<Users> userOptional = null;
            if(user.getGroupId() == null)
                user.setGroupId(user.getUserType());
            else
                user.setGroupId( user.getGroupId() );
            if(user.getId()!= null)
                userOptional = userRepository.findById(user.getId());
            else userOptional = null;
            if (userOptional == null){
                Integer totalUsers = userRepository.findAll().size();
                LocalDate localDate = LocalDate.now();
                String year = Integer.toString(localDate.getYear());
                String userType = null;
                if (user.getUserType()!=null) userType = Integer.toString(user.getUserType());
                if (user.getPassword()!=null)
                    user.setPassword( passwordEncoder.encode(user.getPassword()) );
                user.setIsFirstLogin( true );
            }
            // for updating if user exists
            else {
                Users userFound = userOptional.get();
                if (user.getPassword()!=null)
                    user.setPassword( passwordEncoder.encode(user.getPassword()) );
                user.setCreatedBy( userFound.getCreatedBy() );
                user.setCreatedDate( userFound.getCreatedDate() );
                user.setModifiedBy( authService.getCurrentUser().getUsername() );
                user.setModifiedDate( new Date() );
                userRepository.save(user);
                responseMessage.setStatus(SUCCESSFUL_STATUS);
                responseMessage.setText("Updated successfully.");
                return responseMessage;
            }
        } catch (Exception ex) {
            responseMessage.setStatus(UNSUCCESSFUL_STATUS);
            responseMessage.setResponseText("Could not save." + ex);
            return responseMessage;
        }
        user.setCreatedBy(authService.getCurrentUser().getUsername());
        user.setCreatedDate(new Date());
        userRepository.save(user);
        responseMessage.setStatus(SUCCESSFUL_STATUS);
        responseMessage.setText("Saved successfully.");
        return responseMessage;
    }

    public List<Users> getUserList() {
        return userRepository.findAll();
    }

    /*
     * Check whether the user exists or not by userName
     * */
    public ResponseEntity<?> isUserExist(String username) {
//        UserDTO user = userRepository.findByUsername(username);
        if (!Objects.isNull(userRepository.findByUsername(username))) {
            return new ResponseEntity("Users already exist.", HttpStatus.OK);
        } else {
            return new ResponseEntity("Users does not exist.", HttpStatus.FOUND);
        }
    }

    /**
     * for updating user info like email, password, phonenumber using userId
     * @param id
     * @param user
     * @return
     */
    public ResponseEntity<Users> updateByUserId(Integer id, Users user){
        Optional <Users> userList = userRepository.findById(id);
        if(userList.isPresent()){
            Users _userFound = userList.get();
//            if(!user.getEmail().equals(null)) _userFound.setEmail(user.getEmail());
//            if(!user.getPhoneNumber().equals(null)) _userFound.setPhoneNumber(user.getPhoneNumber());
            _userFound.setModifiedBy(authService.getCurrentUser().getUsername());
            _userFound.setModifiedDate(new Date());
            //TODO mail that the info is updated

            return new ResponseEntity<>(userRepository.save(_userFound), OK);
        }
        return new ResponseEntity("Failed", HttpStatus.NOT_FOUND);
    }
}
