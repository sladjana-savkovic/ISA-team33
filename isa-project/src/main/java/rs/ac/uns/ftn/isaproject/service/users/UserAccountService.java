package rs.ac.uns.ftn.isaproject.service.users;

import java.nio.file.AccessDeniedException;

import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.users.User;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public interface UserAccountService {
	UserAccount findById(Long id) throws AccessDeniedException;
    UserAccount findByUsername(String username);
    UserAccount findByUserId(int id);
    void save(String username, String password, String role, boolean enabled, User user);
    void updatePassword(Long id,String oldPassword, String newPassword) throws BadRequestException;
}

