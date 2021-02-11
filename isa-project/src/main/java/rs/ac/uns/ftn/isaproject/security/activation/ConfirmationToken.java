package rs.ac.uns.ftn.isaproject.security.activation;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

@Entity
@Table(name="confirmationToken")
public class ConfirmationToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="token_id")
	private long tokenid;

	@Column(name="confirmation_token")
	private String confirmationToken;
	
	@OneToOne(targetEntity = UserAccount.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "account_id")
	private UserAccount userAccount;

	
	public ConfirmationToken() {}

	public ConfirmationToken(UserAccount userAccount) {
		this.userAccount = userAccount;
		confirmationToken = UUID.randomUUID().toString();
	}

	public long getTokenid() {
		return tokenid;
	}

	public void setTokenid(long tokenid) {
		this.tokenid = tokenid;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}


	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
}

