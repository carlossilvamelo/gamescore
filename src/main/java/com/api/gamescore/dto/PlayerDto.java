package com.api.gamescore.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import com.api.gamescore.models.Skill;

public class PlayerDto implements Serializable, Comparator<PlayerDto> {

	private static final long serialVersionUID = 932103284971256781L;

	private String name;
	private Integer level;
	private Long score;
	private List<Skill> skills;

	public PlayerDto() {

	}

	public PlayerDto(String name, Integer level, Long score, List<Skill> skills) {

		this.name = name;
		this.level = level;
		this.score = score;
		this.setSkills(skills);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	@Override
	public int compare(PlayerDto p1, PlayerDto p2) {

		if (p1.getLevel() == p2.getLevel()) {
			if (p1.getScore() > p2.getScore())
				return 1;
			else if (p1.getScore() < p2.getScore())
				return -1;

		} else {
			if (p1.getLevel() > p2.getLevel())
				return 1;
			else if (p1.getLevel() < p2.getLevel())
				return -1;

		}
		return 0;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

}
