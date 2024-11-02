package com.myapp.presentation.service.query

import java.util.*

data class GetUserQuery(
    val userId: UUID
) {

    data class Result(
        val userId: UUID,
        val username: String,
        val posts: List<Post>
    ) {
        data class Post(
            val postId: UUID,
            val content: String,
        )
    }
}
