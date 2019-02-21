package com.api.gamescore.resources;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.gamescore.dto.PlayerDto;
import com.api.gamescore.models.Player;
import com.api.gamescore.services.PlayerService;

@RestController
@RequestMapping("/players")
public class PlayerResource {

	static final Logger LOG = Logger.getLogger(PlayerResource.class);

	@Autowired
	private PlayerService playerService;

	@GetMapping
	public List<PlayerDto> getAll(@RequestParam(defaultValue = "0") String pageNumber,
			@RequestParam(defaultValue = "10") String pageSize, @RequestParam(defaultValue = "") String category) {
		return playerService.getAllWithFilterCategory(pageNumber, pageSize, category).getContent();
	}

	@GetMapping("/{playerAId}/compareto/{playerBId}")
	public Player comparePlayers(@PathVariable(name="playerAId") Long playerAId,
			@PathVariable(name="playerBId") Long playerBId) {
		return playerService.comparePlayers(playerAId, playerBId).get(0);
	}

	@GetMapping("/{playerName}")
	public List<Player> searchPlayerByName(@PathVariable(name = "playerName") String playerName,
			@RequestParam(defaultValue = "0") String pageNumber, @RequestParam(defaultValue = "10") String pageSize) {
		return playerService.searchPlayerByName(playerName, pageNumber, pageSize).getContent();

	}
}
