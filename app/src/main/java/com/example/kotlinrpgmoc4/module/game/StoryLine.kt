package com.example.kotlinrpgmoc4.module.game

enum class StoryLine {
    WELCOME_TO_THE_GAME,
    PLAYER_PSEUDO,
    START_QUEST,
    ANSWER_QUEST,
    DUNGEON_INFORMATION,
    CHOOSE_WEAPON,
    CONFIGURE_PLAYER,
    START_EXPLORATION;

    companion object {
        fun getNextStoryLinePoint(point: StoryLine): StoryLine = values()[point.ordinal + 1]
    }
}