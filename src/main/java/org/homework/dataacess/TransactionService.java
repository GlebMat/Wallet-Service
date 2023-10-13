package org.homework.dataacess;

import org.homework.domain.Client;
import org.homework.domain.Transaction;
import org.homework.domain.TypeTransaction;
import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;

import java.util.HashMap;
import java.util.Map;

public class TransactionService {
    private static IdTransaction idTransaction = new IdTransaction();
    private static Map<Integer, Transaction> transactions = new HashMap<>();


    public static void debit(double withdrow, Client client) throws BigDebitException, UniqueIdException {

        if (client.getBalance() - withdrow < 0) {
            throw new BigDebitException("Not enough funds for withdrawal");
        }
        client.setBalance(client.getBalance() - withdrow);
        int uId = idTransaction.getId();

        if (transactions.get(uId) != null) {
            throw new UniqueIdException("The passed ID is not unique");
        }
        Transaction transaction = new Transaction(TypeTransaction.DEBIT, withdrow);
        transactions.put(uId, transaction);
        client.getTransactions().put(uId, transaction);
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));
    }

    /**
     * Perform a credit transaction, adding funds to the client's balance.
     *
     * @param credit The amount of credit to add to the balance.
     * @throws UniqueIdException If the transaction ID is not unique.
     */
    public static void credit(double credit, Client client) throws UniqueIdException {
        int uId = idTransaction.getId();
        if (transactions.get(uId) != null) {
            throw new UniqueIdException("The passed ID is not unique");
        }
        client.setBalance(client.getBalance() + credit);
        Transaction transaction = new Transaction(TypeTransaction.CREDIT, credit);
        transactions.put(uId, transaction);
        client.getTransactions().put(uId, transaction);
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));
    }

    /**
     * Display the transaction history of the client.
     */
    public static void history(Client client) {
        for (Map.Entry<Integer, Transaction> entry : client.getTransactions().entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
