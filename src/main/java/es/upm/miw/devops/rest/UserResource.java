package es.upm.miw.devops.rest;

import es.upm.miw.devops.rest.models.User;
import es.upm.miw.devops.rest.dtos.UserUpdateDto;
import es.upm.miw.devops.rest.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id){
        this.userService.deleteById(id);
    }

    @PutMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setActive(@PathVariable String id, @RequestBody boolean active) {
        this.userService.setActive(id, active);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody UserUpdateDto userUpdateDto) {
        this.userService.update(id, userUpdateDto);
    }

}