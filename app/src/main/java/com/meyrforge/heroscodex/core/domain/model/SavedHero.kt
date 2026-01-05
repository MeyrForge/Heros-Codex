package com.meyrforge.heroscodex.core.domain.model

import java.util.UUID

data class SavedHero(
    val id: UUID,
    val name: String,
    val race: Race,
    val gender: Gender,
    val background: Background,
    val createdAt: Long
)
