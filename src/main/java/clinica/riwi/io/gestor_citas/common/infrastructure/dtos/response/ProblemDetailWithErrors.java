package clinica.riwi.io.gestor_citas.common.infrastructure.dtos.response;

import org.springframework.http.ProblemDetail;

import lombok.Getter;

@Getter
public class ProblemDetailWithErrors extends ProblemDetail {
  private ProblemDetailFieldError[] errors;

  public ProblemDetailWithErrors(int status) {
    super(status);
  }
}
