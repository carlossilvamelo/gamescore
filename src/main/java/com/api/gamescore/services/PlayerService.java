package com.api.gamescore.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api.gamescore.dto.PlayerDto;
import com.api.gamescore.enums.SkillCategory;
import com.api.gamescore.exceptions.ResourceNotFoundException;
import com.api.gamescore.models.Player;
import com.api.gamescore.repository.PlayerRepository;

@Service
public class PlayerService {

	static final Logger LOG = Logger.getLogger(PlayerService.class);

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * Get a list of players filtered by category or not.
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param category
	 * @return - list of playerDto
	 */
	public Page<PlayerDto> getAllWithFilterCategory(String pageNumber, String pageSize, String category) {
		final String categoryUpperCase = category.toUpperCase();
		List<String> categories = Arrays.asList(SkillCategory.values()).stream().map(c -> c.name())
				.collect(Collectors.toList());
		Pageable pageRequest = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
		Page<Player> playerList = null;
		List<PlayerDto> playerDto = null;

		if (categories.contains(categoryUpperCase)) {

			LOG.info(String.format("Listing players by filter %s", categoryUpperCase));
			playerList = playerRepository.findAllWithFilter(categoryUpperCase, pageRequest);

			playerDto = playerList.stream()
					.map(player -> new PlayerDto(player.getName(), player.getLevel(),
							player.getSkills().get(0).getScore(),
							player.getSkills().stream()
									.filter(s -> s.getSkillCategory().name().equals(categoryUpperCase))
									.collect(Collectors.toList())))
					.collect(Collectors.toList());
		} else {

			LOG.info("Listing players without filter");
			playerList = playerRepository.findAll(pageRequest);
			playerDto = playerList.stream()
					.map(player -> new PlayerDto(player.getName(), player.getLevel(),
							player.getSkills().stream().mapToLong(skill -> skill.getScore()).sum(), player.getSkills()))
					.collect(Collectors.toList());
		}

		LOG.info("Sorting list of players");
		Collections.sort(playerDto, (a, b) -> b.compare(b, a));

		return new PageImpl<>(playerDto);
	}

	/**
	 * Search a player by name, verify if the string is contained ou is exactly the
	 * same.(case insensitive)
	 * 
	 * @param playerName
	 * @param pageNumber
	 * @param pageSize
	 * @return list of Player
	 */
	public Page<Player> searchPlayerByName(String playerName, String pageNumber, String pageSize) {
		Pageable pageRequest = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));

		LOG.info(String.format("Searching player with '%s' in the name", playerName));

		return playerRepository.findOneByNameContainingIgnoreCase(playerName, pageRequest);
	}

	/**
	 * Compare two players and return the best one.
	 * 
	 * @param playerAId
	 * @param playerBId
	 * @return - list of Player ordered by the best
	 */
	public List<Player> comparePlayers(Long playerAId, Long playerBId) {
		Player playerA = playerRepository.findById(playerAId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("There is no player with id = %d", playerAId)));
		Player playerB = playerRepository.findById(playerBId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("There is no player with id = %d", playerBId)));

		LOG.info(String.format("Comparing players with ids %d and %d", playerAId, playerBId));
		List<Player> playerList = Arrays.asList(playerA, playerB);
		Collections.sort(playerList, (p1, p2) -> p1.compare(p2, p1));

		return playerList;
	}
}
