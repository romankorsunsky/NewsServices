package none.romank.authserv;

import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class UserRegistrationValidatorImpl implements UserRegistrationValidator{
    private UserRegistrationValidatorImpl(){}
    /*
     * @returns an Optional with the UserRegistration object inside if it is validated,
     * or an Optional.empty() if the UserRegistration was bad.
     */
    @Override
    public Optional<UserRegistration> validate(UserRegistration userReg) {
        final Logger log = LogManager.getLogger();
        if(userReg == null || userReg.getUsername() == null || userReg.getEmail() == null || userReg.getPassword() == null 
            || userReg.getFirstName() == null || userReg.getLastName() == null)
        {
            return Optional.empty();
        }
        Pattern usernameRegex = Pattern.compile("^[a-zA-Z]{1}+[a-zA-Z0-9]++$");
        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_-]{1,16}+(\\.[a-zA-Z0-9_-]{1,16}+)*+@[a-zA-Z]{4,12}+\\.(com|uk|edu|ua|au|ger|fr)$");
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern digits = Pattern.compile("[0-9]");
        Pattern fl_nameRegex = Pattern.compile("^([a-zA-Z]{1}+)[a-z]{1,20}+$"); //enforces both first AND last names
        
        String username = userReg.getUsername();
        String password = userReg.getPassword();
        String email = userReg.getEmail();
        String fname = userReg.getFirstName();
        String lname = userReg.getLastName();

        //password must have length <= 30, start with a letter, contain uppercase,lowercase and digit.
        //absence of any of the mentioned properties warrants returning an empty Optional.
        if(password.length() < 10 ||
            password.length() > 30 ||
            !upperCase.matcher(password).find() ||
            !lowerCase.matcher(password).find() ||
            !digits.matcher(password).find())
        {   
            log.info("BAD PASSWORD !");
            return Optional.empty();
        }

        //username must start with a letter, followed by letters and numbers, length in range [6..16] (inclusive)
        if(username.length() < 6 ||
            username.length() > 16 ||
            !usernameRegex.matcher(username).matches())
        {
            log.info("BAD USERNAME !");
            return Optional.empty();
        }

        if(!emailRegex.matcher(email).matches())
        {
            log.info("BAD EMAIL !");
            return Optional.empty();
        }
        if(!fl_nameRegex.matcher(lname).matches() || !fl_nameRegex.matcher(fname).matches())
        {
            log.info("BAD FIRST MAME OR LAST NAME !");
            return Optional.empty();
        }
        return Optional.of(userReg);
    }

    private static class InstanceHolder{
        private static final UserRegistrationValidatorImpl INSTANCE = new UserRegistrationValidatorImpl();
    }
    public static UserRegistrationValidatorImpl getInstance(){
        return InstanceHolder.INSTANCE;
    }
}
