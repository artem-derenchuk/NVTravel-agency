package com.aderenchuk.enter.repo;


import com.aderenchuk.enter.entity.Tour;
import com.aderenchuk.enter.entity.dto.TourDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TourDaoJPA extends JpaRepository<Tour, Integer > {
    /**
     * Get all tours with quantity clients.
     *
     * @return Tours list.
     */
    @Query("SELECT t.tourId, t.direction, t.dateTour, COUNT(t.tourId)\n" +
            "FROM TourDto t\n" +
            "INNER JOIN Client c ON c.tourId = t.tourId \n" +
            "GROUP BY c.tourId")
    List<TourDto> findAllQuantityClients();

    /**
     * Get all tours with quantity clients and Date Filter.
     *
     * @return Tours list.
     * @param dateFrom
     * @param dateTo
     */
//    @Query("SELECT t.tourId, t.direction, t.dateTour, COUNT(t.tourId)\n" +
//            "FROM TourDto t\n" +
//            "WHERE t.date_tour BETWEEN :dateFrom AND :dateTo\n" +
//            "JOIN Client c ON c.tourId = t.tourId \n" +
//            "GROUP BY c.tourId")
//    List<TourDto> findAllQuantityClientsAndDateFilter(Date dateFrom, Date dateTo);
}
