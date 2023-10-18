package io.github.lucianodacunha.model;

import lombok.*;
import lombok.extern.flogger.Flogger;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "pedidos")
@NamedQuery(
        name = "Pedido.totalDeVendas",
        query = "SELECT SUM(p.valorTotal) FROM Pedido p")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data = LocalDate.now();
    @NonNull
    @ManyToOne // (fetch = FetchType.LAZY)
    private Cliente cliente;
    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido item){
        item.setPedido(this);
        this.itens.add(item);
        this.valorTotal = this.valorTotal.add(item.getValor());
    }

    @Override
    public String toString(){
        return String.format("ID: %d Valor: %5.2f\n%s",
                this.id, this.getValorTotal(),
                this.itens);
    }
}
