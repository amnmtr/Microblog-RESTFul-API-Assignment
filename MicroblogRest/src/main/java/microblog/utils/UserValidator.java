package microblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import microblog.models.User;
import microblog.services.UserService;

@Component("userValidator")
public class UserValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (!StringUtils.isEmpty(user.getUsername())) {
            if (userService.usernameExists(user.getUsername())) {
                errors.rejectValue("username", "Registered");
            }
        }
    }
}
