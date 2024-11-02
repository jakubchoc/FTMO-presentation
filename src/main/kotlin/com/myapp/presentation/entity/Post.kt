package com.myapp.presentation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Entity
class Post(
    id: UUID = UUID.randomUUID(),
    val content: String,
    val userId: UUID,
) {

    @Id
    @Column(updatable = false)
    var id: UUID = id

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: Instant = Instant.now()

    @UpdateTimestamp
    var updatedAt: Instant = Instant.now()
        private set
}

@Repository
interface PostRepository : JpaRepository<Post, UUID>

