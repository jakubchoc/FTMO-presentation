package com.myapp.presentation.service

import com.myapp.presentation.jooq.GetUserJooq
import com.myapp.presentation.service.query.GetUserQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val getUserJooq: GetUserJooq,
    private val dummyService: DummyService,
) {

    @Transactional(readOnly = true)
    fun getUserLeftJoin(getUserQuery: GetUserQuery): GetUserQuery.Result {
        var value = dummyService.getValue()
        return getUserJooq.getUserByIdLeftJoin(getUserQuery)
    }

    @Transactional(readOnly = true)
    fun getUserInnerSelect(getUserQuery: GetUserQuery): GetUserQuery.Result {
        var value = dummyService.getValue()
        return getUserJooq.getUserByIdDoubleSelect(getUserQuery)
    }

    @Transactional(readOnly = true)
    fun getUserMultiset(getUserQuery: GetUserQuery): GetUserQuery.Result  {
        var value = dummyService.getValue()
        return getUserJooq.getUserByIdMultiset(getUserQuery)
    }
}