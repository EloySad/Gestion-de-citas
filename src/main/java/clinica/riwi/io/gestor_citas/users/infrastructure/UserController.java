package clinica.riwi.io.gestor_citas.users.infrastructure;

import clinica.riwi.io.gestor_citas.common.infrastructure.dtos.response.ProblemDetailWithErrors;
import clinica.riwi.io.gestor_citas.users.application.UserService;
import clinica.riwi.io.gestor_citas.users.domain.UserEntity;
import clinica.riwi.io.gestor_citas.users.infrastructure.dtos.response.RegisterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Read All Users", description = "Description: Read all users from database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User read all is successful", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponseDto.class)) }),
      @ApiResponse(responseCode = "400", description = "The request body has invalid values", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailWithErrors.class)) }) })
  @GetMapping
  public ResponseEntity<List<UserEntity>> findAll() {
    List<UserEntity> users = userService.findAll();
    return ResponseEntity.ok(users);
  }

  @Operation(summary = "Read user by id", description = "Description: Read an especific user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User read is successful", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponseDto.class)) }),
      @ApiResponse(responseCode = "400", description = "The request body has invalid values", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailWithErrors.class)) }),
      @ApiResponse(responseCode = "409", description = "User dont exist", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)) }), })
  @GetMapping("/{id}")
  public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
    return userService.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Update user", description = "Description: Update an exist user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User update is successful", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponseDto.class)) }),
      @ApiResponse(responseCode = "400", description = "The request body has invalid values", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailWithErrors.class)) }) })
  @PutMapping("/{id}")
  public ResponseEntity<UserEntity> update(@PathVariable Long id, @RequestBody UserEntity user) {
    UserEntity updatedUser = userService.update(user);
    return ResponseEntity.ok(updatedUser);
  }

  @Operation(summary = "Delete user", description = "Description: Delete an exist user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User delete is successful", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponseDto.class)) }),
      @ApiResponse(responseCode = "400", description = "The request body has invalid values", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetailWithErrors.class)) }),
      @ApiResponse(responseCode = "409", description = "User dont exist", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)) }), })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
