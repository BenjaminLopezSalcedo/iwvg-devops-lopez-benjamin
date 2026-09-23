package es.upm.miw.devops.rest;

import es.upm.miw.devops.rest.models.User;
import es.upm.miw.devops.rest.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return this.userService.findById(id);
    }

    @GetMapping
    public List<User> findBillable(@RequestParam boolean billable) {
        return this.userService.findByBillable(billable);
    }
}