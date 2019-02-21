package com.api.gamescore.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.api.gamescore.enums.SkillCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SKILL")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(name = "skill_category")
	private SkillCategory skillCategory;
	@Column(name = "score")
	private Long score;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "player_fk")
	@JsonIgnore
	private Player player;

	public Skill() {

	}

	public Skill(SkillCategory skillCategory, Long score) {
		this.skillCategory = skillCategory;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SkillCategory getSkillCategory() {
		return skillCategory;
	}

	public void setSkillCategory(SkillCategory skillCategory) {
		this.skillCategory = skillCategory;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
