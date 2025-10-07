package com.example.leetpeek

data class SubmissionsData(
    val acSubmissionNum: List<AcSubmissionNum>,
    val easySolved: Int,
    val hardSolved: Int,
    val mediumSolved: Int,
    val solvedProblem: Int,
    val totalSubmissionNum: List<TotalSubmissionNum>
)