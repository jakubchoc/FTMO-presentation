package com.myapp.presentation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Entity
@Table(name = "\"user\"")
class User(
    id: UUID = UUID.randomUUID(),
    val name: String,
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
interface UserRepository : JpaRepository<User, UUID>