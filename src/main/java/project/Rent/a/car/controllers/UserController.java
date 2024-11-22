package project.Rent.a.car.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.Rent.a.car.http.AppResponse;
import project.Rent.a.car.models.UserModel;
import project.Rent.a.car.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createNewUser(@RequestBody UserModel user){
        HashMap<String, Object> response = new HashMap<>();

        if (this.userService.createUser(user)){
            return AppResponse.success().withMessage("user created successfully").build();
        }

        return AppResponse.error().withMessage("user cant be createddd").build();

    }
    @GetMapping("users")
    public ResponseEntity<?> fetchAllUsers(){

        ArrayList<UserModel> collection = (ArrayList<UserModel>) this.userService.getAllUsers();

        return AppResponse.success().withData(collection).build();

    }
}
