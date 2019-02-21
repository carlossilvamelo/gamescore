package com.api.gamescore.models;

import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYER")
public class Player implements Comparator<Player> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "level")
	private Integer level;
	@Column(name = "skills")
	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

	private List<Skill> skills;

	public Player() {

	}

	public Player(String name, Integer level, List<Skill> skills) {
		super();
		this.name = name;
		this.level = level;
		this.skills = skills;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public int compare(Player p1, Player p2) {
		Long scoreP1 = p1.getSkills().stream().mapToLong(s -> s.getScore()).sum();
		Long scoreP2 = p2.getSkills().stream().mapToLong(s -> s.getScore()).sum();

		if (p1.getLevel() == p2.getLevel()) {
			if (scoreP1 > scoreP2)
				return 1;
			else if (scoreP1 < scoreP2)
				return -1;

		} else {
			if (p1.getLevel() > p2.getLevel())
				return 1;
			else if (p1.getLevel() < p2.getLevel())
				return -1;

		}
		return 0;
	}

}
