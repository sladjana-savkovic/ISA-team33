package rs.ac.uns.ftn.isaproject.service.users;

import java.nio.file.AccessDeniedException;

import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.users.User;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public interface UserAccountService {
	UserAccount findById(int id) throws AccessDeniedException;
    UserAccount findByUsername(String username);
    void save(String username, String password, String role, boolean enabled, User user);
    void updatePassword(int id,String oldPassword, String newPassword) throws BadRequestException;
}

