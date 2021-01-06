package io.rk.nac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.rk.nac.model.Holding;

@Repository
public interface HoldingsRepo extends JpaRepository<Holding, Integer> {
    @Query(nativeQuery = true, value = "from Holding h where h.firmName=:firmName and h.onDate=:onDate")
    public List<Holding> getHoldings(@Param("firmName") String firmName, @Param("onDate") String date);

    @Query(value = "from Holding h where h.onDate=:onDate")
    public List<Holding> getHoldingsByDate(@Param("onDate") String onDate);
}
