package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.appUser.AppUserFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.AppUserResponseDTO;
import com.joshuadias.moneyplannerapi.enums.RoleEnum;
import com.joshuadias.moneyplannerapi.services.AppUserService;
import com.joshuadias.moneyplannerapi.utils.OrderByUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/app-user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/pageable")
    public ResponseEntity<Page<AppUserResponseDTO>> getAppUserPageable(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email
    ) {
        OrderByUtils.validateOrderBy(orderBy);
        var filter = new AppUserFilterRequestDTO();
        filter.setEmail(email);
        filter.setName(name);
        filter.setPage(page);
        filter.setSize(size);
        filter.setOrderBy(orderBy);

        return new ResponseEntity<>(appUserService.getAllAppUsersPageable(filter), OK);
    }

    @GetMapping("/current")
    public ResponseEntity<AppUserResponseDTO> getCurrentAppUser(Principal principal) {
        return new ResponseEntity<>(appUserService.getCurrentAppUser(principal.getName()), OK);
    }

    @Secured(RoleEnum.ADMIN_STR)
    @PostMapping("/{id}/role/{roleId}")
    public ResponseEntity<AppUserResponseDTO> addRole(@PathVariable Long id, @PathVariable Long roleId) {
        return new ResponseEntity<>(appUserService.addRoleToAppUser(id, roleId), CREATED);
    }
}
