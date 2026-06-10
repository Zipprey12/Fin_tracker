package com.skillbox.data.repository.mappers;

import com.skillbox.data.model.dto.Account;
import com.skillbox.data.model.enums.AccountType;
import com.skillbox.service.utils.StringUtil;

public class AccountLineMapper implements LineMapper<Account>{

    @Override
    public Account mapToDto(String string) {
        String[] parts = string.split("\\s*,\\s*");

        if (parts.length != 3) {
            return null;
        }

        var id = StringUtil.parseToInt(parts[0]);
        var type = StringUtil.parseToInt(parts[1]);
        var user = StringUtil.parseToInt(parts[2]);

        if (id.isEmpty() || type.isEmpty() || user.isEmpty()) {
            return null;
        }

        int typeId = type.get();
        if(typeId >= AccountType.TYPES_COUNT || typeId < 0){
            return null;
        }

        return Account.builder()
                .accountId(id.get())
                .accountType(AccountType.of(typeId))
                .userId(user.get())
                .build();
    }
}
