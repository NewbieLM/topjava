package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Override
    <S extends Meal> S save(S s);

    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    Optional<Meal> get(@Param("id") Integer id, @Param("userId") Integer userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    Integer delete(@Param("id") Integer id, @Param("userId") Integer userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> findByDatetimeBetweenAndUserid(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("userId") int userId);
}
