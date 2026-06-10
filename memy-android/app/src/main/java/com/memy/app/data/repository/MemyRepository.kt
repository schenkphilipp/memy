package com.memy.app.data.repository

import com.memy.app.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * In-memory repository.  Replace with Room + Retrofit/Ktor for production.
 */
class MemyRepository {

    private val _memys       = MutableStateFlow(sampleMemys)
    private val _collections = MutableStateFlow(sampleCollections)

    val memys:       StateFlow<List<Memy>>       = _memys.asStateFlow()
    val collections: StateFlow<List<Collection>> = _collections.asStateFlow()

    val currentUser = sampleUser

    // ── Memys ─────────────────────────────────────────────────────────────
    fun getMemyById(id: String): Memy? = _memys.value.find { it.id == id }

    fun addMemy(memy: Memy) {
        _memys.update { it + memy }
    }

    fun deleteMemy(id: String) {
        _memys.update { list -> list.filter { it.id != id } }
    }

    fun search(query: String): List<Memy> {
        if (query.isBlank()) return _memys.value
        val q = query.lowercase()
        return _memys.value.filter { m ->
            m.name.lowercase().contains(q) ||
            m.tags.any { it.lowercase().contains(q) } ||
            m.location?.label?.lowercase()?.contains(q) == true ||
            m.description?.lowercase()?.contains(q) == true
        }
    }

    fun memysForCollection(collectionId: String): List<Memy> =
        _memys.value.filter { collectionId in it.collectionIds }

    // ── Collections ───────────────────────────────────────────────────────
    fun getCollectionById(id: String): Collection? =
        _collections.value.find { it.id == id }

    fun addCollection(collection: Collection) {
        _collections.update { it + collection }
    }
}
