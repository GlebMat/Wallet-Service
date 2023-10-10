package org.homework.domain;

import java.util.HashMap;
import java.util.UUID;

public class Transaction {
     private HashMap<Integer, String> transaction = new HashMap<>();
     public Transaction(int id, String operation){
        transaction.put(id,operation);
     }

}
