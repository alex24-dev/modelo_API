package br.com.snba.models.dtos.padraoespecificacao;

import br.com.snba.models.dtos.SituacaoDTO;
import br.com.snba.models.dtos.atributoespec.AtributoEspecificacaoDTO;
import br.com.snba.models.dtos.subclasse.SubClasseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PadraoEspecificacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Id do padrao especificacao", example = "04012022")
    private Long id;

    @ApiModelProperty(value = "Nome do padrao especificacao",example = "frutas")
    private String nome;

    private List<AtributoEspecificacaoDTO> atributos;

    private SubClasseDTO subClasse;

    @ApiModelProperty(value = "Situação : ATIVADO | DESATIVADO | EM_ELABORACAO")
    private SituacaoDTO situacao;

    @ApiModelProperty(value = "A data/hora de inclusão do registro.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataHoraInclusao;

    @ApiModelProperty(value = "A data/hora de atualização do registro.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataHoraAtualizacao;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
