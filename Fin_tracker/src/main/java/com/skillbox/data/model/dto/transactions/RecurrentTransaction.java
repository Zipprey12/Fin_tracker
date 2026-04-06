package com.skillbox.data.model.dto.transactions;

import com.skillbox.data.model.abstractions.Recurring;
import com.skillbox.data.model.enums.RecurrencePattern;
import com.skillbox.data.model.enums.TransactionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@EqualsAndHashCode(callSuper = true)
public class RecurrentTransaction extends Transaction implements Recurring {

    private RecurrencePattern recurrence;
    private int paymentsCount;

    @Getter
    private LocalDateTime lastPayment;

    public RecurrentTransaction() {
        super(TransactionType.RECURRENT);
    }

    @Override
    public LocalDateTime getNextOccurrence(LocalDateTime dateTime) {
        return findNext(dateTime);
    }

    @Override
    public LocalDateTime getPreviousOccurrence(LocalDateTime dateTime) {
        return findPrevious(dateTime);
    }

    @Override
    public BigDecimal getTransactionAmount(LocalDateTime dateTime) {
        var index = findLastIndexLessOrEqual(dateTime);
        if (index == -1) {
            return BigDecimal.ZERO;
        }
        return getAmount().multiply(BigDecimal.valueOf(index + 1L));
    }

    @Override
    public boolean isExecutedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null) {
            return true;
        }

        if (startDate != null && startDate.isAfter(lastPayment)) {
            return false;
        }

        if (endDate == null) {
            return findFirstIndexGreaterOrEqual(startDate) != -1;
        }

        if (startDate == null) {
            return findLastIndexLessOrEqual(endDate) != -1;
        }

        var after = findFirstIndexGreaterOrEqual(startDate);
        var before = findLastIndexLessOrEqual(endDate);

        if (after == -1 || before == -1) {
            return false;
        }
        return (after <= before);
    }

    public void setRecurrence(RecurrencePattern recurrence) {
        this.recurrence = recurrence;
        calculateLastPayment();
    }

    public void setPaymentsCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество платежей не может быть меньше 0");
        }
        this.paymentsCount = count;
        calculateLastPayment();
    }

    private void calculateLastPayment() {
        if (recurrence == null || paymentsCount <= 0) {
            lastPayment = null;
            return;
        }
        lastPayment = createPaymentDate(paymentsCount - 1);
    }


    private LocalDateTime findNext(LocalDateTime before) {
        if (before.isAfter(lastPayment)) {
            return null;
        }
        if (before.isBefore(getDateTime())) {
            return getDateTime();
        }

        int index = findFirstIndexGreaterOrEqual(before);
        if (index == -1) {
            return null;
        }

        var current = createPaymentDate(index);
        if (current.isEqual(before)) {
            index++;
        }
        if (index >= paymentsCount) {
            return null;
        }
        return createPaymentDate(index);
    }

    private LocalDateTime findPrevious(LocalDateTime after) {
        if (after.isBefore(getDateTime())) {
            return null;
        }
        if (after.isAfter(lastPayment)) {
            return lastPayment;
        }

        int index = findLastIndexLessOrEqual(after);
        if (index == -1) {
            return null;
        }

        var current = createPaymentDate(index);
        if (current.equals(after)) {
            index--;
        }

        if (index < 0) {
            return null;
        }
        return createPaymentDate(index);
    }

    private int findFirstIndexGreaterOrEqual(LocalDateTime target) {
        int left = 0;
        int right = paymentsCount - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            var current = createPaymentDate(mid);

            if (current.isAfter(target) || current.isEqual(target)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    private int findLastIndexLessOrEqual(LocalDateTime target) {
        int left = 0;
        int right = paymentsCount - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            var current = createPaymentDate(mid);

            if (current.isBefore(target) || current.isEqual(target)) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    private LocalDateTime createPaymentDate(int index) {
        long count = (long) index * recurrence.getUnitsCount();
        return getDateTime().plus(count, recurrence.getChronoUnit());
    }
}
