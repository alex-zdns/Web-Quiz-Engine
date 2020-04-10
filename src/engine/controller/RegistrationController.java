package engine.controller;

import engine.entity.Users;
import engine.exceptions.BadRequest;
import engine.exceptions.NotFoundException;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/register")
    public void addUser(@Valid @RequestBody Users user) {
        Users userFromDB = userRepository.findByEmail(user.getEmail());

        if (userFromDB != null) {
            throw new BadRequest();
        }

        if (user == null) {
            throw new NotFoundException();
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

}
