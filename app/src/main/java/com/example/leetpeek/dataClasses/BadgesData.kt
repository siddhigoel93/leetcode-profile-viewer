package com.example.leetpeek.dataClasses

import com.example.leetpeek.dataClasses.UpcomingBadge

data class BadgesData(
    val activeBadge: Badge?,
    val badges: List<Badge>,
    val badgesCount: Int,
    val upcomingBadges: List<UpcomingBadge>
)
data class Badge(
    val id: String,
    val displayName: String,
    val icon: String,
    val creationDate: String
)