package com.memy.app.data.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

// ── User ─────────────────────────────────────────────────────────────────────
data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val name: String,
    val phone: String? = null,
    val avatarUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

// ── Location ─────────────────────────────────────────────────────────────────
data class MemyLocation(
    val lat: Double,
    val lng: Double,
    val label: String,
)

// ── Memy ─────────────────────────────────────────────────────────────────────
data class Memy(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val name: String,
    val description: String? = null,
    val url: String? = null,
    val tags: List<String> = emptyList(),
    val photoUrl: String? = null,
    val date: LocalDate = LocalDate.now(),
    val mood: String? = null,           // emoji e.g. "😌"
    val location: MemyLocation? = null,
    val collectionIds: List<String> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

// ── Collection ───────────────────────────────────────────────────────────────
data class Collection(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val name: String,
    val coverUrls: List<String> = emptyList(), // up to 4 memy photos
    val memyCount: Int = 0,
)
