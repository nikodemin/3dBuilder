package com.niko.prokat.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TulipDto<F,S> {
    private F first;
    private S second;
}
