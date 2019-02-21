package com.api.gamescore.services;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import com.api.gamescore.dto.PlayerDto;
import com.api.gamescore.enums.SkillCategory;
import com.api.gamescore.models.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerServiceTest {

	@Autowired
	private PlayerService playerService;

	@Test
	public void searchPlayerByNameTest() {
		Page<Player> playerList = playerService.searchPlayerByName("titan", "0", "10");
		Assertions.assertThat(playerList).isNotNull();
		playerList.forEach(p -> Assertions.assertThat(p.getName().toLowerCase()).contains("titan".toLowerCase()));
	}

	@Test
	public void getAllWithCategoryFilterAndPaginationTest() {
		final String CATEGORY = SkillCategory.ATTACK.toString();
		final Integer pageSize = 30;

		Page<PlayerDto> players = playerService.getAllWithFilterCategory("0", pageSize.toString(), CATEGORY);

		Assertions.assertThat(players.getContent().size()).isEqualTo(pageSize);

		players.forEach(player -> {
			Assertions.assertThat(player).isNotNull();

			player.getSkills().forEach(skill -> {
				Assertions.assertThat(skill).isNotNull();
				Assertions.assertThat(skill.getSkillCategory().name()).isEqualTo(CATEGORY);
			});

		});

	}

	@Test
	public void compareTwoPlayersTest() {

		for (Long playerId = 1L; playerId <= 20; playerId++) {
			List<Player> players = playerService.comparePlayers(playerId, playerId + 1);
			Assertions.assertThat(players).isNotNull();
			Player bestPlayer = players.get(0);
			Player worstPlayer = players.get(1);
			Assertions.assertThat(bestPlayer.compare(bestPlayer, worstPlayer)).isIn(0, 1);
		}

	}

}
