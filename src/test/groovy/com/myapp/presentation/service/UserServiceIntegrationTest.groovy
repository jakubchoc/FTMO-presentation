package com.myapp.presentation.service

import com.myapp.presentation.IntegrationTest
import com.myapp.presentation.service.query.GetUserQuery
import org.springframework.beans.factory.annotation.Autowired

import static com.myapp.presentation.UuidUtil.toUUID

class UserServiceIntegrationTest extends IntegrationTest {

    @Autowired
    UserService underTest

    def "givenUserId_whenGetUserLeftJoin_shouldReturnUserWithPosts"() {
        given: "create user and posts"
            def user = testHelper.getUser(id: toUUID(1), name: "Jon Doe")
            def post1 = testHelper.getPost(id: toUUID(2), content: "some content 1", userId: user.id)
            def post2 = testHelper.getPost(id: toUUID(3), content: "some content 2", userId: user.id)

        when: "call get user inner select with user id"
            var result = underTest.getUserLeftJoin(new GetUserQuery(user.id))

        then: "should return user with correct id and all his posts"
            verifyAll(result) {
                it.userId == toUUID(1)
                it.posts.size() == 2
                it.username == "Jon Doe"
                verifyAll(it.posts.find{ it.postId == toUUID(2) }) {
                    it.content == "some content 1"
                }
                verifyAll(it.posts.find{ it.postId == toUUID(3) }) {
                    it.content == "some content 2"
                }
            }

        and: "dummy method was called and return string"
        1 * dummyService.getValue() >> "hello"
    }

    def "givenUserId_whenGetUserDoubleSelect_shouldReturnUserWithPosts"() {
        given: "create user and posts"
            def user = testHelper.getUser(id: toUUID(1), name: "Jon Doe")
            def post1 = testHelper.getPost(id: toUUID(2), content: "some content 1", userId: user.id)
            def post2 = testHelper.getPost(id: toUUID(3), content: "some content 2", userId: user.id)

        when: "call get user inner select with user id"
            var result = underTest.getUserInnerSelect(new GetUserQuery(user.id))

        then: "should return user with correct id and all his posts"
            verifyAll(result) {
                it.userId == toUUID(1)
                it.posts.size() == 2
                it.username == "Jon Doe"
                verifyAll(it.posts.find{ it.postId == toUUID(2) }) {
                    it.content == "some content 1"
                }
                verifyAll(it.posts.find{ it.postId == toUUID(3) }) {
                    it.content == "some content 2"
                }
            }

        and: "dummy method was called and return string"
            1 * dummyService.getValue() >> "hello"
    }

    def "givenUserId_whenGetUserMultiset_shouldReturnUserWithPosts"() {
        given: "create user and posts"
            def user = testHelper.getUser(id: toUUID(1), name: "Jon Doe")
            def post1 = testHelper.getPost(id: toUUID(2), content: "some content 1", userId: user.id)
            def post2 = testHelper.getPost(id: toUUID(3), content: "some content 2", userId: user.id)

        when: "call get user multiset with user id"
            var result = underTest.getUserMultiset(new GetUserQuery(user.id))

        then: "should return user with correct id and all his posts"
            verifyAll(result) {
                it.userId == toUUID(1)
                it.posts.size() == 2
                verifyAll(it.posts.find{ it.postId == toUUID(2) }) {
                    it.content == "some content 1"
                }
                verifyAll(it.posts.find{ it.postId == toUUID(3) }) {
                    it.content == "some content 2"
                }
            }

        and: "dummy method was called and return string"
            1 * dummyService.getValue() >> "hello"
    }
}
