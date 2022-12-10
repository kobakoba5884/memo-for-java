package minmin.koba.spring.whatisDI.services;

import minmin.koba.spring.whatisDI.models.User;

public interface UserService {
    void register(User user, String password);
}
