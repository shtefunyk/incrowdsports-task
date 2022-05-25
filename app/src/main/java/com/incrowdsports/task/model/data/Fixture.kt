package com.incrowdsports.task.model.data

data class Fixture(
    val id: String,
    val feedMatchId: Long,
    val competition: String,
    val period: String,
    val date: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val venue: Venue,
)

data class Team(
    val id: String,
    val name: String,
    val score: String,
    val players: List<Player>
)

data class Player(
    val known: String
)

data class Venue(
    val id: String,
    val name: String,
)

data class DetailInfo(
    val data: DetailData
)

data class DetailData(
    val competition: String,
    val season: String,
    val homeTeam: Team,
    val awayTeam: Team
)