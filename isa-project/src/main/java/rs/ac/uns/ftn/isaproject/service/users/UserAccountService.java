package rs.ac.uns.ftn.isaproject.service.users;

import java.util.List;

import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public interface UserAccountService {
    UserAccount findById(Long id);
    UserAccount findByUsername(String username);
    List<UserAccount> findAll ();
}

