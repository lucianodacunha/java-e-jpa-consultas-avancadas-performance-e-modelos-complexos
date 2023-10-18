package io.github.lucianodacunha.bancojpa.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "AGENCIA")
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Integer numero;
    @OneToMany(mappedBy = "agencia", cascade = CascadeType.ALL)
    private List<Conta> contas = new ArrayList<>();
    @NonNull
    private String cidade;

    public void cadastrarConta(Conta conta){
        this.contas.add(conta);
        conta.setAgencia(this);
    }

    @Override
    public String toString(){
        return "Agencia(id=%d, numero=%d, cidade=%s)"
                .formatted(this.getId(), this.getNumero(), this.getCidade());
    }
}
