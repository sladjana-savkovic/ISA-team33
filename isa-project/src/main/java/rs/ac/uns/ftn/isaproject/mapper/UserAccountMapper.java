package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.UserAccountDTO;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public class UserAccountMapper {

	public static UserAccountDTO toAccountDTO(UserAccount userAccount) {
		return new UserAccountDTO(userAccount.getId(), userAccount.getUsername(), userAccount.getUser().getId());
	}
}
