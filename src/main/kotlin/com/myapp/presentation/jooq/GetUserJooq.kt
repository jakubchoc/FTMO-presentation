package com.myapp.presentation.jooq

import com.myapp.presentation.service.query.GetUserQuery
import com.myapp.presentation.tables.Post.POST
import com.myapp.presentation.tables.User.USER
import org.jooq.DSLContext
import org.jooq.impl.DSL.*
import org.springframework.stereotype.Service

@Service
class GetUserJooq(
    private val dslContext: DSLContext,
) {

    fun getUserByIdLeftJoin(getUserQuery: GetUserQuery): GetUserQuery.Result {
        return dslContext
            .select(
                USER.ID,
                USER.NAME,
                POST.ID,
                POST.CONTENT
            )
            .from(USER)
            .leftJoin(POST).on(POST.USER_ID.eq(USER.ID))
            .where(USER.ID.eq(getUserQuery.userId))
            .fetch()
            .groupBy { it[USER.ID]!! }
            .mapValues {
                val userRecord = it.value.first()
                GetUserQuery.Result(
                    userId = userRecord[USER.ID]!!,
                    username = userRecord[USER.NAME]!!,
                    posts = it.value.mapNotNull { post ->
                        GetUserQuery.Result.Post(
                            postId = post[POST.ID]!!,
                            content = post[POST.CONTENT]!!
                        )
                    }
                )
            }.values.firstOrNull() ?: throw Exception()
    }

    fun getUserByIdDoubleSelect(getUserQuery: GetUserQuery): GetUserQuery.Result {
        val socialNetworksSelect = dslContext.select(
            POST.ID,
            POST.CONTENT
        )
            .from(POST)
            .where(POST.USER_ID.eq(getUserQuery.userId))
            .fetch()

        return dslContext
            .select(
                USER.ID,
                USER.NAME,
            ).from(USER)
            .where(USER.ID.eq(getUserQuery.userId))
            .fetchOne()
            ?.map {
                GetUserQuery.Result(
                    userId = it[USER.ID]!!,
                    username = it[USER.NAME]!!,
                    posts = socialNetworksSelect.map { post ->
                        GetUserQuery.Result.Post(
                            postId = post[POST.ID]!!,
                            content = post[POST.CONTENT]!!
                        )
                    }
                )
            } ?: throw Exception()
    }

    fun getUserByIdMultiset(getUserQuery: GetUserQuery): GetUserQuery.Result {
        return dslContext
            .select(
                USER.ID,
                USER.NAME,
                socialNetworksMultiset
            ).from(USER)
            .where(USER.ID.eq(getUserQuery.userId))
            .fetchOne()
            ?.map {
                GetUserQuery.Result(
                    userId = it[USER.ID]!!,
                    username = it[USER.NAME]!!,
                    posts = it[socialNetworksMultiset].map { post ->
                        GetUserQuery.Result.Post(
                            postId = post[POST.ID]!!,
                            content = post[POST.CONTENT]!!
                        )
                    }
                )
            } ?: throw Exception()
    }

    val socialNetworksMultiset = multiset(
        select(
            POST.ID,
            POST.CONTENT,
            //POST.CREATED_AT //BUG
        )
            .from(POST)
            .where(POST.USER_ID.eq(USER.ID)),
    )
}