package com.skillbox.data.model.dto.transactions;

import com.skillbox.data.model.abstractions.Commentable;
import com.skillbox.data.model.enums.TransactionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CommentableTransaction extends Transaction implements Commentable {

    private String comment;

    public CommentableTransaction() {
        super(TransactionType.COMMENTABLE);
    }
}
