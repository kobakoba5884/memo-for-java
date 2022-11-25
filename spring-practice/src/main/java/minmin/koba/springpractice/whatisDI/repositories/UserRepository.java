package minmin.koba.springpractice.whatisDI.repositories;

import minmin.koba.springpractice.whatisDI.models.User;

public interface UserRepository {
    User save(User user);

    int countByUsername(String username);
}
