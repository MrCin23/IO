package pl.lodz.p.ias.io.uwierzytelnianie.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.exceptions.ForbiddenException;
import pl.lodz.p.ias.io.uwierzytelnianie.exceptions.UnauthorizedException;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new UnauthorizedException("Wrong credentials");
        }

        if (!account.isActive()) {
            throw new ForbiddenException("User is not active");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(account.getUsername())
                .password(account.getPasswordHash())
                .roles(account.getRole().getRoleName())
                .build();
    }
}
