package com.myapp.presentation

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

@Service
@Profile("test")
@ContextConfiguration
class DatabaseCleanupService {

    @PersistenceContext
    private val entityManager: EntityManager? = null

    val TRUNCATE_ALL_TABLES_SQL: String = """
				DO
				${'$'}${'$'} DECLARE r RECORD;
				BEGIN
					FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
						IF r.tablename <> 'flyway_schema_history' THEN
							EXECUTE 'TRUNCATE TABLE ' || quote_ident(r.tablename) || ' RESTART IDENTITY CASCADE';
						END IF;
					END LOOP;
				END ${'$'}${'$'};;
			
			""".trimIndent()

    @Transactional
    fun truncate() {
        entityManager!!.createNativeQuery(TRUNCATE_ALL_TABLES_SQL).executeUpdate()
    }
}