package com.kotlin.jpa

import com.kotlin.jpa.domain.Member
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.FlushModeType

@SpringBootApplication
class JpaApplication

fun main(args: Array<String>) {
	runApplication<JpaApplication>(*args)
}
