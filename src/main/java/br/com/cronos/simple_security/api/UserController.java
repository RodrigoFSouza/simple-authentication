package br.com.cronos.simple_security.api;

import br.com.cronos.simple_security.domain.dto.UserDTO;
import br.com.cronos.simple_security.domain.dto.request.UserUpdateRequestDTO;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.domain.specification.UserSpecification;
import br.com.cronos.simple_security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(summary = "List users", description = "Get a paginated list of users with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<Page<UserDTO>> listUsers(
            @Parameter(description = "Filter by first name") @RequestParam(required = false) String firstname,
            @Parameter(description = "Filter by last name") @RequestParam(required = false) String lastname,
            @Parameter(description = "Filter by email") @RequestParam(required = false) String email,
            @Parameter(description = "Filter by enabled status") @RequestParam(required = false) Boolean enabled,
            @Parameter(description = "Pagination parameters") @PageableDefault(size = 10, sort = "firstname") Pageable pageable) {

        Specification<User> spec = UserSpecification.filterBy(firstname, lastname, email, enabled);
        Page<UserDTO> userPage = userService.findAllUsers(spec, pageable);
        return ResponseEntity.ok(userPage);
    }

    @Operation(summary = "Get user by ID", description = "Get a single user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        UserDTO user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update user", description = "Update an existing user's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated user",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id,
            @Parameter(description = "User update data", required = true) @Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        UserDTO updatedUser = userService.updateUser(id, userUpdateRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Note: A POST endpoint for creating users via /api/users could be added here
    // using userService.createUser(userCreateDTO)
} 