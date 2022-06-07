package bt.org.dsp.crst.userManager.controller;

import bt.org.dsp.crst.base.BaseController;
import bt.org.dsp.crst.lib.ResponseMessage;
import bt.org.dsp.crst.userManager.dto.UserDTO;
import bt.org.dsp.crst.userManager.model.UserGroup;
import bt.org.dsp.crst.userManager.model.Users;
import bt.org.dsp.crst.userManager.service.PasswordPolicyService;
import bt.org.dsp.crst.userManager.service.UserGroupService;
import bt.org.dsp.crst.userManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController extends BaseController {
    private UserService userService;
    private UserGroupService userGroupService;
    private final PasswordPolicyService passwordPolicyService;

    /**
     * get all the user groups
     * @return user groups
     */
    @GetMapping(value = "/getGroupList")
    public List<UserGroup> getGroupList() {
        return userGroupService.findAll();
    }

    /**
     * save user
     * @param user
     * @return responseMessage
     */
    @PostMapping(value = "/save")
    public ResponseMessage save(@RequestBody Users user) {
        return userService.save(user);
    }

    /**
     * get list of user
     * @return user list
     */
    @GetMapping(value = "/getUserList")
    public ResponseEntity<List<Users>> getUserList() {
        return ResponseEntity.status(OK).body(userService.getUserList());
    }

    /**
     * Check whether the user exists or not by username
     * @param userName
     * @return
     */
    @GetMapping(value = "/isUserExist/{userName}")
    @ResponseBody
    public ResponseEntity<?> isUserExist(@PathVariable String userName) {
        return userService.isUserExist(userName);
    }

    /**
     * Update user by ID. Submit email, phone number
     * @param id
     * @param user
     * @return
     */
    @PostMapping(value="/update/{id}")
    private ResponseEntity<Users> updateByUserId(@PathVariable Integer id, @RequestBody Users user){
        return userService.updateByUserId(id, user);
    }

    /**
     * Change password
     * @param userDTO
     * @return
     */
    @PostMapping(value="/changePassword")
    private ResponseEntity<UserDTO> changePassword(@RequestBody UserDTO userDTO){
        return passwordPolicyService.changePassword(userDTO);
    }


}
