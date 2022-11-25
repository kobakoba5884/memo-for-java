package minmin.koba.springpractice.whatisDI.services;

import minmin.koba.springpractice.whatisDI.models.User;

public interface UserService {
    void register(User user, String password);
}
