package clinica.riwi.io.gestor_citas.users.infrastructure.dtos.response;

import clinica.riwi.io.gestor_citas.common.infrastructure.dtos.response.ApiResponseDto;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class RegisterResponseDto extends ApiResponseDto {
  public RegisterResponseDto(int value, String string) {
    super(value, string);
  }
}