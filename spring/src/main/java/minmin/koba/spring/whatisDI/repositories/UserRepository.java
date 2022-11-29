package minmin.koba.spring.whatisDI.repositories;

import minmin.koba.spring.whatisDI.models.User;

public interface UserRepository {
    User save(User user);

    int countByUsername(String username);
}
