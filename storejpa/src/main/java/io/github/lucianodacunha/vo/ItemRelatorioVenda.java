package io.github.lucianodacunha.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class ItemRelatorioVenda {
    private String nome;
    private Long quantidade;
    private LocalDate data;
}
