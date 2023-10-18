package io.github.lucianodacunha.bancojpa.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "CONTA")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Integer numero;
    @NonNull
    @ManyToOne
    private Agencia agencia;
    @NonNull
    @ManyToOne
    private Cliente cliente;

    private BigDecimal saldo;

    @Override
    public String toString(){
        return "Conta(id=%d, numero=%d, cliente=%s"
                .formatted(this.getId(), this.getNumero(),
                        this.getCliente().getNome());
    }
}
