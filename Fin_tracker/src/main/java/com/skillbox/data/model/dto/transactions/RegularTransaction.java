package com.skillbox.data.model.dto.transactions;

import com.skillbox.data.model.enums.TransactionType;

public class RegularTransaction extends Transaction{

    public RegularTransaction(){
        super(TransactionType.REGULAR);
    }
}
