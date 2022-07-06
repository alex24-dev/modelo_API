package br.com.snba.models.dtos.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaginationResponseDTO<T> {

    private List<T> content;
    private Integer pageSize;
    private Integer pageIndex;
    private Integer totalPages;
    private Integer contentSize;
    private Long totalElements;
    private Boolean first;
    private Boolean last;
    private Boolean empty;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
