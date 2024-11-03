package command;

import model.Account;
import model.Transaction;

import java.util.List;

public class RemoveTransactionCommand implements Command{

    List<Transaction> transactionsToRemove;
    Account account;

    public RemoveTransactionCommand(List<Transaction> transactionToRemove, Account account) {
        this.transactionsToRemove = transactionToRemove;
        this.account = account;
    }

    @Override
    public void execute() {
        account.getTransactions().removeAll(transactionsToRemove);
    }

    @Override
    public String getName() {
        return transactionsToRemove.size() + " transactions removed";
    }

}
