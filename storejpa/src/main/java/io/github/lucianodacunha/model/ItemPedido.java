package io.github.lucianodacunha.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;
    @NonNull
    private Integer quantidade;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Pedido pedido;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    public ItemPedido(Integer quantidade, Pedido pedido, Produto produto){
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.produto = produto;
        this.precoUnitario = produto.getPreco();
    }

    public BigDecimal getValor(){
        return precoUnitario.multiply(new BigDecimal(quantidade));
    }

    @Override
    public String toString(){
        return "%03d %5.2f %03d %s".formatted(
                this.id, this.getPrecoUnitario(), this.getQuantidade(),
                this.produto.getNome()
        );
    }
}
