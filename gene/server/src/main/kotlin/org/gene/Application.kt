package com.bftcom.web.server

import com.bftcom.ice.server.cli.CliService
import com.bftcom.ice.server.mappings.AsIsNameMappingsStrategy
import com.bftcom.ice.server.mappings.NameMappingsStrategy
import com.bftcom.ice.server.services.DbDialect
import com.bftcom.ice.server.services.SqlStatistics
import com.bftcom.ice.server.services.getDbDialectByConnection
import com.bftcom.ice.server.util.JdbcTemplateWrapper
import com.bftcom.ice.server.util.NamedJdbcTemplateWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.annotation.Resource

@EnableAutoConfiguration
@SpringBootApplication
@ImportResource("classpath:spring-config.xml")
open class Application : SpringBootServletInitializer() {

    @Resource
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var sqlStatistics: SqlStatistics

    @Bean
    open fun nameMappingsStrategy(): NameMappingsStrategy {
        return AsIsNameMappingsStrategy()
    }

    @Bean
    open fun namedPrameterJdbcTemplate(): NamedParameterJdbcOperations {
        val t = JdbcTemplateWrapper(jdbcTemplate, sqlStatistics)
        return NamedJdbcTemplateWrapper(NamedParameterJdbcTemplate(t), sqlStatistics)
    }

    @Bean
    open fun dbDialect(): DbDialect {
        return getDbDialectByConnection(jdbcTemplate)
    }

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(javaClass)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ctx = SpringApplication.run(Application::class.java, *args)

            ctx.getBean(CliService::class.java).startCommandLine()
        }
    }
}