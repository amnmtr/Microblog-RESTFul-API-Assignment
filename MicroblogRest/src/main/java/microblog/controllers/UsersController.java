package microblog.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import microblog.models.User;
import microblog.services.UserService;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator userValidator;

    @PostMapping("/register")
    public String registerUser(@Validated({User.CreateValidationGroup.class}) @ModelAttribute(value = "user") User user, BindingResult result, HttpSession session) {
        user.setUsername(StringUtils.trimWhitespace(user.getUsername()));

        userValidator.validate(user, result);

        if (result.hasErrors())
        {
            return "registration";
        }

        userService.register(user);

        userService.authenticate(user);

        Object regRef = session.getAttribute("regRef");

        return "redirect:" + (StringUtils.isEmpty(regRef) ? "posts" : regRef.toString());
    }

    @GetMapping("/check_username")
    public @ResponseBody String checkUsername(@RequestParam("username") String username) {
        return userService.usernameExists(username) ? "false" : "true";
    }
    
}