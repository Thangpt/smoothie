package com.thangpt.researching.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelModel {
    private String rowA;

    private String rowB;

    private Integer rowC;

    private Long rowD;

    private Date rowE;
}
