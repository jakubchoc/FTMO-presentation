package com.myapp.presentation

import com.myapp.presentation.entity.Post
import com.myapp.presentation.entity.PostRepository
import com.myapp.presentation.entity.User
import com.myapp.presentation.entity.UserRepository
import groovy.transform.NamedParam
import groovy.transform.NamedVariant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class IntegrationTestDataHelper {

    @Autowired
    UserRepository userRepository

    @Autowired
    PostRepository postRepository

    @NamedVariant
    User getUser(
            @NamedParam UUID id = UUID.randomUUID(),
            @NamedParam String name = "name"
    ) {
        return userRepository.save(
                new User(
                        id,
                        name
                )
        )
    }

    @NamedVariant
    Post getPost(
            @NamedParam UUID id = UUID.randomUUID(),
            @NamedParam String content = "content",
            @NamedParam UUID userId
    ) {
        return postRepository.save(
                new Post(
                        id,
                    content,
                    userId
                )
        )
    }
}
