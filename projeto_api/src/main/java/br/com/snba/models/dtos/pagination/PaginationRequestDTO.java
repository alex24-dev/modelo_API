package br.com.snba.models.dtos.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PaginationRequestDTO {

    private String query;
    private Integer pageSize;
    private Integer pageIndex;
    private String sort;

}
