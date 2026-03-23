package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

        @Query(value = """
    SELECT COALESCE(SUM(amount),0)
    FROM payments
    WHERE payment_status='SUCCESS'
    AND payment_date >= CURDATE()
    AND payment_date < CURDATE() + INTERVAL 1 DAY
    """, nativeQuery = true)
        Double fetchTodayRevenue();


        @Query(value = """
    SELECT COALESCE(SUM(amount),0)
    FROM payments
    WHERE payment_status='SUCCESS'
    AND MONTH(payment_date)=MONTH(CURDATE())
    AND YEAR(payment_date)=YEAR(CURDATE())
    """, nativeQuery = true)
        Double fetchMonthlyRevenue();


        @Query(value = """
    SELECT COALESCE(SUM(amount),0)
    FROM payments
    WHERE payment_status='SUCCESS'
    AND YEAR(payment_date)=YEAR(CURDATE())
    """, nativeQuery = true)
        Double fetchYearlyRevenue();
    }