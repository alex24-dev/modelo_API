package br.com.snba.models.dtos.padraoespecificacao;

import br.com.snba.models.dtos.subclasse.SubClasseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class AtualizarPadraoEspecificacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 150)
    @ApiModelProperty(value = "O nome do atributo da especificação.", example = "Marca", required = true)
    private String nome;

    @ApiModelProperty(value = "id subclasse.", example = "1", required = true)
    private Long subclasseId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
