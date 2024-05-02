package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.appUser.AppUserFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.appUser.AppUserRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.AppUserResponseDTO;
import com.joshuadias.moneyplannerapi.services.AppUserService;
import com.joshuadias.moneyplannerapi.utils.OrderByUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/app-user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/pageable")
    @ResponseStatus(OK)
    public Page<AppUserResponseDTO> getAppUserPageable(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email
    ) {
        OrderByUtils.validateOrderBy(orderBy);
        var filter = AppUserFilterRequestDTO
                .builder()
                .email(email)
                .name(name)
                .page(page)
                .size(size)
                .orderBy(orderBy)
                .build();
        return appUserService.getAllAppUsersPageable(filter);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public AppUserResponseDTO createAppUser(@RequestBody AppUserRequestDTO appUserRequest) {
        return appUserService.createAppUser(appUserRequest);
    }

    @PostMapping("/{id}/role/{roleId}")
    @ResponseStatus(CREATED)
    public AppUserResponseDTO addRole(@PathVariable Long id, @PathVariable Long roleId) {
        return appUserService.addRoleToAppUser(id, roleId);
    }
}
