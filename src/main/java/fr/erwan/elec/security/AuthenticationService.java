package fr.erwan.elec.security;

import java.nio.CharBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Classe de service pour gérer la sécurité
 */
@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;

    @Value("${application.login}")
    private String login;

    @Value("${application.pwd}")
    private String pwd;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Vérification des identifiants
     * @param credentialsDto login et password
     * @return un user login et password
     */
    public UserDto authenticate(CredentialsDto credentialsDto) {
        String encodedMasterPassword = passwordEncoder.encode(CharBuffer.wrap(this.pwd));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), encodedMasterPassword)) { 
            return new UserDto(this.login, this.pwd);
        }
        throw new RuntimeException("Invalid password");
    }

    /**
     * Gère le login sans le password
     * @param login l'utilisateur
     * @return un user
     */
    public UserDto findByLogin(String login) {
        if (this.login.equals(login)) {
            return new UserDto(this.login, this.pwd);
        }
        throw new RuntimeException("Invalid login");
    }
}
