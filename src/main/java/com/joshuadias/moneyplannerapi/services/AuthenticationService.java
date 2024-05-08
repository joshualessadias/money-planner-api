package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.dto.requests.appUser.AuthenticationRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.appUser.RegisterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.AuthenticationResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.models.AppUser;
import com.joshuadias.moneyplannerapi.security.config.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.joshuadias.moneyplannerapi.enums.RoleEnum.USER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private AppUser buildAppUserFromRequest(RegisterRequestDTO appUserRequest) {
        var appUser = new AppUser();
        appUser.setFirstName(appUserRequest.getFirstName());
        appUser.setLastName(appUserRequest.getLastName());
        appUser.setEmail(appUserRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        return appUser;
    }

    private void checkIfEmailIsAlreadyRegistered(String email) {
        if (appUserService.existsByEmail(email)) {
            throw new BadRequestException(MessageEnum.APP_USER_EMAIL_ALREADY_REGISTERED.getMessage(email));
        }
    }

    @Transactional
    public AppUser createAppUser(RegisterRequestDTO appUserRequest) {
        log.info(MessageEnum.APP_USER_CREATING.getMessage());
        checkIfEmailIsAlreadyRegistered(appUserRequest.getEmail());
        var appUser = buildAppUserFromRequest(appUserRequest);
        var createdAppUser = appUserService.save(appUser);
        log.info(MessageEnum.APP_USER_CREATED_WITH_ID.getMessage(String.valueOf(appUser.getId())));
        return appUserService.addRoleToAppUserNotConvertingToDto(createdAppUser.getId(), USER.getId());
    }

    @Transactional
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        var appUser = createAppUser(request);
        var jwtToken = jwtService.generateToken(appUser);
        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var appUser = appUserService.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(appUser);
        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }
}
