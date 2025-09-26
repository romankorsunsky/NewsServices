package none.romank.backend.api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import none.romank.backend.Transaction;
import none.romank.backend.TransactionDTO;
import none.romank.backend.TransactionRepository;
import none.romank.backend.User;
import none.romank.backend.UserDetailsRepository;


@RestController
@RequestMapping(path="/api/transactions",produces="application/json")
@CrossOrigin(origins={"localhost:8080"})
public class TransactionController {

    
    private TransactionRepository trans_repo;
    private UserDetailsRepository user_repo;
    @Autowired
    public TransactionController(TransactionRepository transactionRepository,UserDetailsRepository userRepo){
        this.trans_repo = transactionRepository;
        this.user_repo = userRepo;
    }
    public TransactionController(){

    }

    @PostMapping(path="addtransaction",consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO addTransaction(@RequestBody Transaction transaction) {
        Transaction t = trans_repo.save(transaction);
        TransactionDTO tdto = new TransactionDTO(t.getId(),t.getAmount(),t.getAccount().getId());
        //return ResponseEntity.ok(tdto);
        return tdto;
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transaction>> getTransaction(@PathVariable("id") Long acc_id){
        Optional<User> usr = user_repo.findById(acc_id);
        if(!usr.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(usr.get().getTransactions(),HttpStatus.OK);
        }
    }
}
