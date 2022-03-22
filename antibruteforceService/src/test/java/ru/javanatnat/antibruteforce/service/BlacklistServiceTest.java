package ru.javanatnat.antibruteforce.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.javanatnat.antibruteforce.config.DataJdbcConfig;
import ru.javanatnat.antibruteforce.repository.BlacklistRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {DataJdbcConfig.class, BlacklistServiceTest.Config.class})
public class BlacklistServiceTest {
    @Container
    private static final PostgreSQLContainer<ListsPostgreSQLContainer> postgresqlContainer
            = ListsPostgreSQLContainer.getInstance();

    private static final String IP = "128.11.0.1";

    static class Config {
        @Autowired
        BlacklistRepository repository;

        @Bean
        BlacklistService service() {
            return new BlacklistServiceImpl(repository);
        }
    }

    @Autowired
    BlacklistService service;

    @Test
    void addIpBlacklistTest() {
        service.addIpBlacklist(IP);
        assertThat(service.isInList(IP)).isTrue();
    }

    @Test
    void deleteIpBlacklistTest() {
        service.addIpBlacklist(IP);
        assertThat(service.isInList(IP)).isTrue();
        service.deleteIpBlacklist(IP);
        assertThat(service.isInList(IP)).isFalse();
    }

    @Test
    void cashBlacklistTest() {
        service.addIpBlacklist(IP);
        assertThat(service.isInList(IP)).isTrue();
        service.cashBlacklist();
        assertThat(service.isInList(IP)).isTrue();

        service.deleteIpBlacklist(IP);
        assertThat(service.isInList(IP)).isFalse();
        service.cashBlacklist();
        assertThat(service.isInList(IP)).isFalse();
    }
}
