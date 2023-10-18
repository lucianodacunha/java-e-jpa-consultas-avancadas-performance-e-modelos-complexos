package io.github.lucianodacunha.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String cpf;

    @Override
    public String toString(){
        return "%03d %-30s %s".formatted(this.id, this.nome, this.cpf);
    }
}
