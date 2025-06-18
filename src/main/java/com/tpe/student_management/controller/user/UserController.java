package com.tpe.student_management.controller.user;

import com.tpe.student_management.payload.request.user.UserRequestDTO;
import com.tpe.student_management.payload.request.user.UserRequestDTOWithoutPassword;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.abstracts.BaseUserResponse;
import com.tpe.student_management.payload.response.user.UserResponseDTO;
import com.tpe.student_management.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save/{userRole}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<UserResponseDTO>> saveUser(@Valid @RequestBody UserRequestDTO dto,
                                                                     @PathVariable String role){
        return new ResponseEntity<>(userService.saveUser(dto, role), HttpStatus.CREATED);
    }

    @GetMapping("/get-all/{userRole}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> getUsersByPage(@PathVariable String userRole,
                                                                @RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "25") int size,
                                                                @RequestParam(defaultValue = "name") String sortBy,
                                                                @RequestParam(defaultValue = "ASC") Sort.Direction order){
        Page<UserResponseDTO> adminsOrDeans = userService.getUsersByPage(page, size, sortBy, order, userRole);
        return new ResponseEntity<>(adminsOrDeans, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);//
    }

    // ****************     deleteUserById()
    @DeleteMapping("/delete/{id}") //http://localhost:8080/user/delete/3
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id ,
                                                 HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(userService.deleteUserById(id , httpServletRequest));
    }
    // ****************     updateDeanOrViceDean()
    // ---Sadece admin'in cagirabilecegi, MANAGER ve ASSISTANT_MANAGER rolundeki kullanicilari
    // ---update edecegi bir metod.
    @PutMapping("/update/{userId}") // http://localhost:8080/user/update/1
    @PreAuthorize("hasAuthority('ADMIN')")
    //!!! donen deger BaseUserResponse --> polymorphism
    public ResponseMessage<BaseUserResponse> updateAdminDeanViceDeanForAdmin(
            @RequestBody @Valid UserRequestDTO userRequest,
            @PathVariable Long userId){
        return userService.updateDeanOrViceDean(userRequest,userId);
    }

    // ****************     updateUserOwnInfo()
    // ---SIFRE HARIC kullanicinin kendi bilgisini guncellemesini saglayan metod. //
    @PatchMapping("/updateUser")   // http://localhost:8080/user/updateUser
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public ResponseEntity<String>updateUserOwnInfo(@RequestBody @Valid
                                                UserRequestDTOWithoutPassword userRequestWithoutPassword,
                                            HttpServletRequest request){
        return userService.updateUserOwnInfo(userRequestWithoutPassword, request);
    }
    // ****************     getUserByFirstName()
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getUserByFirstName")   // http://localhost:8080/user/getUserByFirstName?firstName=user1
    public List<UserResponseDTO> getUserByFirstName(@RequestParam(name = "firstName") String firstName){
        return userService.getUserByFirstName(firstName);
    }
}
