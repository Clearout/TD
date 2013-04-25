package com.example.towerdefence;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public class Score {
	private ArrayList<String> level1ScoresString, level2ScoresString,
			level3ScoresString, survivalScoresString;
	private ArrayList<Integer> level1Scores, level2Scores, level3Scores,
			survivalScores;
	private SharedPreferences data;

	public Score(Game game) {
		data = game.getPreferences(Context.MODE_PRIVATE);

		level1ScoresString = new ArrayList<String>();
		level2ScoresString = new ArrayList<String>();
		level3ScoresString = new ArrayList<String>();
		survivalScoresString = new ArrayList<String>();

		level1Scores = new ArrayList<Integer>();
		level2Scores = new ArrayList<Integer>();
		level3Scores = new ArrayList<Integer>();
		survivalScores = new ArrayList<Integer>();

		for (int i = 0; i < 10; i++) {
			level1ScoresString.add("level1Score" + (i + 1));
			level2ScoresString.add("level2Score" + (i + 1));
			level3ScoresString.add("level3Score" + (i + 1));
			survivalScoresString.add("survivalScore" + (i + 1));

			level1Scores.add(data.getInt(level1ScoresString.get(i), -1));
			level2Scores.add(data.getInt(level2ScoresString.get(i), -1));
			level3Scores.add(data.getInt(level3ScoresString.get(i), -1));
			survivalScores.add(data.getInt(survivalScoresString.get(i), -1));
		}

	}

	public void addScore(int score, int level) {
		switch (level) {
		case 1:
			for (int i = 0; i < level1Scores.size(); i++) {
				if (level1Scores.get(i) == -1) {
					level1Scores.add(i, score);
					return;
				} else {
					if (level1Scores.get(i) > score)
						continue;
					else {
						level1Scores.add(i, score);
						return;
					}
				}
			}
		case 2:
			for (int i = 0; i < level2Scores.size(); i++) {
				if (level2Scores.get(i) == -1) {
					level2Scores.add(i, score);
					return;
				} else {
					if (level2Scores.get(i) > score)
						continue;
					else {
						level2Scores.add(i, score);
						return;
					}
				}
			}
		case 3:
			for (int i = 0; i < level3Scores.size(); i++) {
				if (level3Scores.get(i) == -1) {
					level3Scores.add(i, score);
					return;
				} else {
					if (level3Scores.get(i) > score)
						continue;
					else {
						level3Scores.add(i, score);
						return;
					}
				}
			}
		case -1:
			for (int i = 0; i < survivalScores.size(); i++) {
				if (survivalScores.get(i) == -1) {
					survivalScores.add(i, score);
					return;
				} else {
					if (survivalScores.get(i) > score)
						continue;
					else {
						survivalScores.add(i, score);
						return;
					}
				}
			}
		}
	}

	public void saveScores() {
		SharedPreferences.Editor editor = data.edit();
		for (int i=0; i< 10; i++) {
			editor.putInt(level1ScoresString.get(i), level1Scores.get(i));
			editor.putInt(level2ScoresString.get(i), level2Scores.get(i));
			editor.putInt(level3ScoresString.get(i), level3Scores.get(i));
			editor.putInt(survivalScoresString.get(i), survivalScores.get(i));
		}
		editor.commit();
	}

	public ArrayList<Integer> getLevel1Scores() {
		return level1Scores;
	}

	public ArrayList<Integer> getLevel2Scores() {
		return level2Scores;
	}

	public ArrayList<Integer> getLevel3Scores() {
		return level3Scores;
	}

	public ArrayList<Integer> getSurvivalScores() {
		return survivalScores;
	}
}
