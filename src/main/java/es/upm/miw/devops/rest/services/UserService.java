package es.upm.miw.devops.rest.services;

import es.upm.miw.devops.rest.models.User;
import es.upm.miw.devops.rest.dtos.UserUpdateDto;
import es.upm.miw.devops.rest.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private boolean hasContent(String value) {
        return value != null && !value.isBlank();
    }

    public boolean isBillable(User user) {
        return hasContent(user.getFirstName())
                && hasContent(user.getFamilyName())
                && hasContent(user.getEmail())
                && hasContent(user.getIdentity())
                && hasContent(user.getAddress())
                && hasContent(user.getCity())
                && hasContent(user.getProvince())
                && hasContent(user.getPostalCode());
    }

    public List<User> findByBillable(boolean billable) {
        return this.userRepository.findAll()
                .stream()
                .filter(user -> isBillable(user) == billable)
                .toList();
    }

    public void deleteById(String id){
        User user = this.findById(id);
        this.userRepository.delete(user);
    }

    public void setActive(String id, boolean active) {
        User user = this.findById(id);
        user.setActive(active);
        this.userRepository.save(user);
    }

    public void update(String id, UserUpdateDto userUpdateDto) {
        User user = this.findById(id);

        user.setFirstName(userUpdateDto.firstName());
        user.setFamilyName(userUpdateDto.familyName());
        user.setEmail(userUpdateDto.email());
        user.setIdentity(userUpdateDto.identity());
        user.setAddress(userUpdateDto.address());
        user.setCity(userUpdateDto.city());
        user.setProvince(userUpdateDto.province());
        user.setPostalCode(userUpdateDto.postalCode());
        user.setActive(userUpdateDto.active());
        user.setRole(userUpdateDto.role());

        this.userRepository.save(user);
    }

}