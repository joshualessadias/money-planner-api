package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.dto.requests.appUser.AppUserFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.appUser.AppUserRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.appUser.RoleRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.AppUserResponseDTO;
import com.joshuadias.moneyplannerapi.dto.responses.RoleResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.AppUser;
import com.joshuadias.moneyplannerapi.models.Role;
import com.joshuadias.moneyplannerapi.repositories.AppUserRepository;
import com.joshuadias.moneyplannerapi.repositories.RoleRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService extends AbstractServiceRepository<AppUserRepository, AppUser, Long> {
    private final RoleRepository roleRepository;

    PropertyMap<AppUser, AppUserResponseDTO> mapEntityToResponseDTO = new PropertyMap<AppUser, AppUserResponseDTO>() {
        @Override
        protected void configure() {
            map().setRolesId(source.getRoles().stream().map(Role::getId).toList());
        }
    };

    private AppUser buildAppUserFromRequest(AppUserRequestDTO appUserRequest) {
        var appUser = new AppUser();
        appUser.setName(appUserRequest.getName());
        appUser.setEmail(appUserRequest.getEmail());
        appUser.setPassword(appUserRequest.getPassword());
        return appUser;
    }

    @Transactional
    public AppUserResponseDTO createAppUser(AppUserRequestDTO appUserRequest) {
        log.info(MessageEnum.APP_USER_CREATING.getMessage());
        var appUser = buildAppUserFromRequest(appUserRequest);
        var createdAppUser = repository.save(appUser);
        log.info(MessageEnum.APP_USER_CREATED_WITH_ID.getMessage(String.valueOf(appUser.getId())));
        return convertToSingleDTO(createdAppUser, AppUserResponseDTO.class);
    }

    private Role buildRoleFromRequest(RoleRequestDTO roleRequest) {
        var role = new Role();
        role.setName(roleRequest.getName());
        return role;
    }

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO roleRequest) {
        log.info(MessageEnum.ROLE_CREATING.getMessage());
        var role = buildRoleFromRequest(roleRequest);
        var createdRole = roleRepository.save(role);
        log.info(MessageEnum.ROLE_CREATED_WITH_ID.getMessage(String.valueOf(role.getId())));
        return convertToSingleDTO(createdRole, RoleResponseDTO.class);
    }

    public AppUser findAppUserByIdOrThrow(Long appUserId) {
        return repository.findById(appUserId)
                .orElseThrow(() -> new NotFoundException(MessageEnum.APP_USER_NOT_FOUND_WITH_ID.getMessage()));
    }

    public Role findRoleByIdOrThrow(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(MessageEnum.ROLE_NOT_FOUND_WITH_ID.getMessage()));
    }

    @Transactional
    public AppUserResponseDTO addRoleToAppUser(Long appUserId, Long roleId) {
        log.info(MessageEnum.ADDING_ROLE_WITH_ID_TO_APP_USER_WITH_ID.getMessage(String.valueOf(roleId)));
        var appUser = findAppUserByIdOrThrow(appUserId);
        var role = findRoleByIdOrThrow(roleId);
        appUser.getRoles().add(role);
        var updatedAppUser = repository.save(appUser);
        log.info(MessageEnum.ROLE_ADDED_TO_APP_USER.getMessage());
        return convertToSingleDTO(updatedAppUser, AppUserResponseDTO.class);
    }

    public AppUser findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(MessageEnum.APP_USER_NOT_FOUND_WITH_EMAIL.getMessage()));
    }

    private void addPredicates(
            AppUserFilterRequestDTO filter,
            ArrayList<Predicate> predicates,
            CriteriaBuilder criteriaBuilder,
            Root<AppUser> from
    ) {
        if (filter.getEmail() != null)
            predicates.add(criteriaBuilder.like(from.get("email"), "%" + filter.getEmail() + "%"));
        if (filter.getName() != null)
            predicates.add(criteriaBuilder.like(from.get("name"), "%" + filter.getName() + "%"));
    }

    private Specification<AppUser> generateSpecification(AppUserFilterRequestDTO filter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            addPredicates(filter, predicates, criteriaBuilder, root);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<AppUserResponseDTO> getAllAppUsersPageable(AppUserFilterRequestDTO filter) {
        log.info(MessageEnum.APP_USER_FINDING_ALL_PAGEABLE.getMessage());
        var sort = getSorting(filter.getOrderBy());
        var pageable = generatePageable(filter.getPage(), filter.getSize(), sort);
        var spec = generateSpecification(filter);
        var pageAppUsers = repository.findAll(spec, pageable);
        log.info(MessageEnum.APP_USER_FOUND_ALL_PAGEABLE.getMessage(String.valueOf(pageAppUsers.getNumberOfElements())));
        return convertToPageDTO(pageAppUsers, AppUserResponseDTO.class, mapEntityToResponseDTO);
    }
}