package io.rk.nac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.rk.nac.model.Price;

@Repository
public interface PricingRepo extends JpaRepository<Price, Integer> {
    @Query(nativeQuery = true, value = "from Price p where p.firmName=:firmName and p.onDate=:onDate")
    public List<Price> getHoldings(@Param("firmName") String firmName, @Param("onDate") String date);

    @Query(value = "from Price p where p.onDate=:onDate")
    public List<Price> getPricingsByDate(@Param("onDate") String date);
}
