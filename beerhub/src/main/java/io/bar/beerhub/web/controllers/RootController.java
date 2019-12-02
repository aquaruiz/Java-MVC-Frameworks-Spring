package io.bar.beerhub.web.controllers;

import io.bar.beerhub.services.factories.LogService;
import io.bar.beerhub.services.factories.RoleService;
import io.bar.beerhub.services.factories.UserService;
import io.bar.beerhub.services.models.LogServiceModel;
import io.bar.beerhub.services.models.RoleServiceModel;
import io.bar.beerhub.services.models.UserServiceModel;
import io.bar.beerhub.web.annotations.PageTitle;
import io.bar.beerhub.web.models.LogViewModel;
import io.bar.beerhub.web.models.RoleListingModel;
import io.bar.beerhub.web.models.UserChangeRoleModel;
import io.bar.beerhub.web.models.UserListingModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/root")
public class RootController extends BaseController{
    private final UserService userService;
    private final LogService logService;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public RootController(UserService userService, LogService logService, ModelMapper modelMapper, RoleService roleService) {
        this.userService = userService;
        this.logService = logService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @GetMapping("/list-all-users")
    public ModelAndView listAllUsers(ModelAndView modelAndView) {
        List<UserServiceModel> users = this.userService.getAllUsers();
        List<UserListingModel> userListing = users
                .stream()
                .map(u -> this.modelMapper.map(u, UserListingModel.class))
                .collect(Collectors.toUnmodifiableList());
        modelAndView.addObject("users", userListing);

        List<RoleServiceModel> roles = this.roleService.findAllRoles();
        List<RoleListingModel> roleListing = roles
                .stream()
                .map(r -> this.modelMapper.map(r, RoleListingModel.class))
                .collect(Collectors.toUnmodifiableList());
        modelAndView.addObject("roles", roleListing);

        return view("root/list-all-users", modelAndView);
    }

    @PostMapping("/add-delete-user-role")
    public ModelAndView addDelUserRole(@ModelAttribute UserChangeRoleModel userChangeRoleModel) {
       this.userService.addDelUserRole(userChangeRoleModel);
        return redirect("list-all-users");

    }

    @GetMapping("/list-all-logs")
    @PageTitle("Logs records")
    public ModelAndView listAllLogs(ModelAndView modelAndView) {
        List<LogServiceModel> logs = this.logService.getAllLogsOrderByDate();
        List<LogViewModel> logListing = logs
                .stream()
                .map(l -> this.modelMapper.map(l, LogViewModel.class))
                .collect(Collectors.toUnmodifiableList());
        modelAndView.addObject("logs", logListing);

        return view("root/list-all-logs", modelAndView);
    }
}