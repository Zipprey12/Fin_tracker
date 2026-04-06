package com.skillbox.data.repository;

import com.skillbox.data.model.dto.Analytic;

public interface StatisticRepository {

    boolean save(Analytic analytic);
}
