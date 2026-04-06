package com.skillbox.data.model.abstractions;

/**
 * Интерфейс для транзакций, к которым могут быть добавлены комментарии.
 */
public interface Commentable {

    /**
     * Возвращает список комментариев, добавленных к транзакции.
     *
     * @return список комментариев.
     */
    String getComment();
}
