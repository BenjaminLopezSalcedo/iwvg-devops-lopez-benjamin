package es.upm.miw.devops.rest.services;

import es.upm.miw.devops.rest.models.User;
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

    public List<User> findBillable() {
        return this.userRepository.findAll()
                .stream()
                .filter(this::isBillable)
                .toList();
    }

}