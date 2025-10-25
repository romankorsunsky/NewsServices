package  none.romank.authserv;

import java.util.Optional;

@FunctionalInterface
public interface UserRegistrationValidator {
    public Optional<UserRegistration> validate(UserRegistration userReg);
}
