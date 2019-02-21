package com.api.gamescore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.api.gamescore.models.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	public Page<Player> findOneByNameContainingIgnoreCase(String name, Pageable pageRequest);

	@Query("SELECT p FROM Player p INNER JOIN p.skills s WHERE s.skillCategory LIKE CONCAT('%',:category,'%')")
	public Page<Player> findAllWithFilter(@Param(value = "category") String category, Pageable pageRequest);
}
