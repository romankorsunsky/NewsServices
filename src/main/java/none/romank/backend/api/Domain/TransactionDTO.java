package none.romank.backend.api.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionDTO {
    private Long id;
    private Integer amount;
    private Long account_id;

    public TransactionDTO(){}
    
    public TransactionDTO(Long id, Integer amnt, Long acc_id){
        this.id = id;
        this.amount = amnt;
        this.account_id = acc_id;
    }
}
