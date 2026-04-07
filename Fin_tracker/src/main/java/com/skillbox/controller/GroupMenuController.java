package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionsGroupingDto;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.enums.GroupType;

public class GroupMenuController extends AbstractMenuController<GroupOption> {
    private final TransactionsGroupingDto groupingDto = new TransactionsGroupingDto();

    protected GroupMenuController() {
        super(GroupOption.class, "Выберите опцию группировки транзакции");
    }

    public TransactionsGroupingDto selectGrouping() {
        var selected = selectMenu();
        groupingDto.setType(GroupType.NONE);

        switch (selected) {
            case EXIT:
                return groupingDto;
            case BY_MONTHS:
                groupingDto.setType(GroupType.BY_MONTH);
                break;
            case BY_YEARS:
                groupingDto.setType(GroupType.BY_YEAR);
                break;
            case BY_DAY_OF_WEEK:
                groupingDto.setType(GroupType.BY_WEEK_DAY);
                break;
            case BY_CATEGORY:
                groupingDto.setType(GroupType.BY_CATEGORY);
                break;
            case EXPENSE_INCOME:
                groupingDto.setType(GroupType.EXPENSE_INCOME);
                break;
            case BY_ACCOUNT_TYPE:
                groupingDto.setType(GroupType.BY_ACCOUNT_TYPE);
                break;
            case BY_USER:
                groupingDto.setType(GroupType.BY_USER);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selected);
        }
        return groupingDto;
    }
}
