package practice.demirbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import practice.demirbank.dto.DataStorage;
import practice.demirbank.dto.UserDTO;
import practice.demirbank.entity.User;
import practice.demirbank.exception.UnauthorizedException;
import practice.demirbank.service.UserService;
import practice.demirbank.util.JwtUtil;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    DataStorage dataStorage;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String sayHello(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping("/login")
    public String getToken(@RequestBody UserDTO userDTO) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword()));
        } catch (Exception e){
            if(userService.getByLogin(userDTO.getLogin()).getBody() != null && dataStorage.blockList.containsKey(userDTO.getLogin())){
                Long count = dataStorage.blockList.get(userDTO.getLogin()).get(0);
                List<Long> list = new ArrayList<>();
                list.add(count+1);
                list.add(System.currentTimeMillis());
                dataStorage.blockList.put(userDTO.getLogin(), list);
                if(dataStorage.blockList.get(userDTO.getLogin()).get(0) >= 10){
                    Date date = new Date(System.currentTimeMillis());
                    long diffInMillies = Math.abs(dataStorage.blockList.get(userDTO.getLogin()).get(1) - System.currentTimeMillis());
                    if(diffInMillies < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))
                        throw new UnauthorizedException("Auth failed! More than 10 invalid tries!"){};
                }
            }
            throw new UnauthorizedException("Auth failed") {};
        }
        if (dataStorage.blockList.containsKey(userDTO.getLogin())){
            dataStorage.blockList.remove(userDTO.getLogin());
        }

        return jwtUtil.generateToken(userDTO.getLogin());
    }

    @PutMapping("/logout")
    public String logout(Principal principal) {
        List<Long> list = new ArrayList<>();
        list.add(0L);
        list.add(System.currentTimeMillis());
        dataStorage.blockList.put(principal.getName(), list);
        return "successfully logout, your token is disable";
    }

}
