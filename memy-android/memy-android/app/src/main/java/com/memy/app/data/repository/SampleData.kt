package com.memy.app.data.repository

import com.memy.app.data.model.*
import java.time.LocalDate

// Photo URLs use Unsplash source — swap for real assets in production.
private const val IMG_SUNSET   = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=600"
private const val IMG_RAMEN    = "https://images.unsplash.com/photo-1569050467447-ce54b3bbc37d?w=600"
private const val IMG_COFFEE   = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=600"
private const val IMG_BOOK     = "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=600"
private const val IMG_MOUNTAIN = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600"
private const val IMG_MARKET   = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=600"
private const val IMG_BEACH    = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=600"
private const val IMG_CITY     = "https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?w=600"

val sampleUser = User(
    id        = "user-001",
    email     = "philipp@example.com",
    name      = "Philipp",
    avatarUrl = "https://api.dicebear.com/8.x/adventurer/png?seed=philipp",
)

val sampleMemys = listOf(
    Memy(
        id          = "memy-001",
        userId      = "user-001",
        name        = "Sunset at Miradouro",
        description = "Caught the golden hour from the viewpoint after climbing all those hills. Worth every step.",
        url         = "maps.app/lisbon-miradouro",
        tags        = listOf("travel", "portugal"),
        photoUrl    = IMG_SUNSET,
        date        = LocalDate.of(2024, 5, 12),
        mood        = "😌",
        location    = MemyLocation(38.7139, -9.1394, "Lisbon"),
        collectionIds = listOf("col-001"),
    ),
    Memy(
        id          = "memy-002",
        userId      = "user-001",
        name        = "Slow morning",
        description = "Perfect cup, perfect light.",
        tags        = listOf("cozy"),
        photoUrl    = IMG_COFFEE,
        date        = LocalDate.of(2024, 2, 2),
        mood        = "☕",
        location    = MemyLocation(47.3769, 8.5417, "Home"),
        collectionIds = listOf("col-003"),
    ),
    Memy(
        id          = "memy-003",
        userId      = "user-001",
        name        = "First proper ramen",
        description = "The real deal. Tonkotsu, 18-hour broth.",
        tags        = listOf("food"),
        photoUrl    = IMG_RAMEN,
        date        = LocalDate.of(2024, 4, 3),
        mood        = "🍜",
        location    = MemyLocation(33.5902, 130.4017, "Hakata"),
        collectionIds = listOf("col-002"),
    ),
    Memy(
        id          = "memy-004",
        userId      = "user-001",
        name        = "The book that changed it",
        description = "Finished it on the train. Couldn't put it down.",
        tags        = listOf("reading"),
        photoUrl    = IMG_BOOK,
        date        = LocalDate.of(2024, 1, 18),
        mood        = "📖",
        collectionIds = listOf("col-004"),
    ),
    Memy(
        id          = "memy-005",
        userId      = "user-001",
        name        = "Ridge walk",
        description = "Crisp air, no crowds.",
        tags        = listOf("travel", "hiking"),
        photoUrl    = IMG_MOUNTAIN,
        date        = LocalDate.of(2023, 8, 21),
        mood        = "⛰️",
        location    = MemyLocation(46.5107, 11.9972, "Dolomites"),
        collectionIds = listOf("col-001"),
    ),
    Memy(
        id          = "memy-006",
        userId      = "user-001",
        name        = "Market finds",
        description = "Spices, textiles, and the best mint tea.",
        tags        = listOf("travel", "food"),
        photoUrl    = IMG_MARKET,
        date        = LocalDate.of(2023, 11, 9),
        mood        = "🧡",
        location    = MemyLocation(31.6295, -7.9811, "Marrakech"),
        collectionIds = listOf("col-001", "col-002"),
    ),
    Memy(
        id          = "memy-007",
        userId      = "user-001",
        name        = "Beach morning",
        description = "Just the waves.",
        tags        = listOf("travel"),
        photoUrl    = IMG_BEACH,
        date        = LocalDate.of(2023, 7, 14),
        mood        = "🌊",
        location    = MemyLocation(35.5018, 24.0138, "Crete"),
        collectionIds = listOf("col-001", "col-005"),
    ),
    Memy(
        id          = "memy-008",
        userId      = "user-001",
        name        = "City from above",
        description = "The whole skyline in one frame.",
        tags        = listOf("travel"),
        photoUrl    = IMG_CITY,
        date        = LocalDate.of(2023, 6, 3),
        mood        = "🌆",
        location    = MemyLocation(48.8566, 2.3522, "Paris"),
        collectionIds = listOf("col-001"),
    ),
)

val sampleCollections = listOf(
    Collection("col-001", "user-001", "Trips",      coverUrls = listOf(IMG_SUNSET, IMG_MOUNTAIN, IMG_CITY, IMG_BEACH), memyCount = 48),
    Collection("col-002", "user-001", "Good eats",  coverUrls = listOf(IMG_RAMEN, IMG_MARKET),  memyCount = 23),
    Collection("col-003", "user-001", "Cozy",       coverUrls = listOf(IMG_COFFEE, IMG_BOOK),   memyCount = 16),
    Collection("col-004", "user-001", "Reading",    coverUrls = listOf(IMG_BOOK),                memyCount = 12),
    Collection("col-005", "user-001", "Beach days", coverUrls = listOf(IMG_BEACH),               memyCount = 9),
    Collection("col-006", "user-001", "2024",       coverUrls = listOf(IMG_SUNSET, IMG_RAMEN, IMG_COFFEE, IMG_BOOK), memyCount = 134),
)
