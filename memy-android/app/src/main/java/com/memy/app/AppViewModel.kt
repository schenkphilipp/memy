package com.memy.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memy.app.data.model.Memy
import com.memy.app.data.model.Collection
import com.memy.app.data.repository.MemyRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    val repo = MemyRepository()

    // ── Auth state ────────────────────────────────────────────────────────
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun login() { _isLoggedIn.value = true }
    fun logout() { _isLoggedIn.value = false }

    // ── Feed ──────────────────────────────────────────────────────────────
    val memys = repo.memys
    val collections = repo.collections
    val currentUser get() = repo.currentUser

    // ── Search ────────────────────────────────────────────────────────────
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val searchResults: StateFlow<List<Memy>> = _searchQuery
        .map { q -> repo.search(q) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onSearchQueryChange(q: String) { _searchQuery.value = q }

    // ── Capture ───────────────────────────────────────────────────────────
    fun saveMemy(memy: Memy) {
        viewModelScope.launch { repo.addMemy(memy) }
    }

    fun deleteMemy(id: String) {
        viewModelScope.launch { repo.deleteMemy(id) }
    }

    // ── Collections ───────────────────────────────────────────────────────
    fun memysForCollection(collectionId: String) =
        repo.memysForCollection(collectionId)

    fun addCollection(collection: Collection) {
        viewModelScope.launch { repo.addCollection(collection) }
    }
}
