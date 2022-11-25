package minmin.koba.springpractice.whatisDI.repositories;

import minmin.koba.springpractice.whatisDI.models.User;

public class JdbcUserRepository implements UserRepository{

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public int countByUsername(String username) {
        return 0;
    }
    
}
