package com.myapp.presentation

import com.myapp.presentation.service.DummyService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
abstract class IntegrationTest extends Specification {

    @Autowired
    DatabaseCleanupService databaseCleanupService

    @Autowired
    IntegrationTestDataHelper testHelper

    @SpringBean
    DummyService dummyService = Mock()

    /**
     * Cleans database and tmp file storage before each test method.
     */
    def setup() {
        databaseCleanupService.truncate()
    }
}
