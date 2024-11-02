package com.myapp.presentation.service

import com.myapp.presentation.IntegrationTest
import com.myapp.presentation.entity.Post
import com.myapp.presentation.entity.PostRepository
import com.myapp.presentation.entity.User
import com.myapp.presentation.entity.UserRepository
import com.myapp.presentation.service.query.GetUserQuery
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired

class UserServiceIntegrationTestInvalid extends IntegrationTest {

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    @Autowired
    PostRepository postRepository

    @SpringBean
    DummyService dummyService = Mock()

    def "givenUserId_whenGetUserDoubleSelect_shouldReturnUserWithPosts"() {
        given:
        def user = new User(UUID.randomUUID(), "Jon Doe")
        userRepository.save(user)
        def post1 = new Post(UUID.randomUUID(), "some content 1", user.id)
        def post2 = new Post(UUID.randomUUID(), "some content 2", user.id)
        postRepository.saveAll([post1, post2])
        when:
        var result = userService.getUserInnerSelect(new GetUserQuery(user.id))
        then:
        verifyAll(result) {
            it.userId == user.id
            it.username == user.name
            it.posts.size() == 2
            it.posts.find{ it.postId == post1.id}.content == post1.content
            it.posts.find{ it.postId == post2.id}.content == post2.content
        }
        and:
        1 * dummyService.getValue() >> "hello"
    }

    def "givenUserId_whenGetUserMultiset_shouldReturnUserWithPosts"() {
        given:
        def user = new User(UUID.randomUUID(), "Jon Doe")
        userRepository.save(user)
        def post1 = new Post(UUID.randomUUID(),"some content 1", user.id)
        def post2 = new Post(UUID.randomUUID(),"some content 2", user.id)
        postRepository.saveAll([post1, post2])
        when:
        var result = userService.getUserMultiset(new GetUserQuery(user.id))
        then:
        verifyAll(result) {
            it.userId == user.id
            it.username == user.name
            it.posts.size() == 2
            it.posts.find{ it.postId == post1.id}.content == post1.content
            it.posts.find{ it.postId == post2.id}.content == post2.content
        }
        and:
        1 * dummyService.getValue() >> "hello"
    }
}
