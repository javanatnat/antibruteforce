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
import ru.javanatnat.antibruteforce.repository.WhitelistRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {DataJdbcConfig.class, WhitelistServiceTest.Config.class})
public class WhitelistServiceTest {
    @Container
    private static final PostgreSQLContainer<ListsPostgreSQLContainer> postgresqlContainer
            = ListsPostgreSQLContainer.getInstance();

    private static final String IP = "128.11.0.1";

    static class Config {
        @Autowired
        WhitelistRepository repository;

        @Bean
        WhitelistService service() {
            return new WhitelistServiceImpl(repository);
        }
    }

    @Autowired
    WhitelistService service;

    @Test
    void addIpBlacklistTest() {
        service.addIpWhitelist(IP);
        assertThat(service.isInList(IP)).isTrue();
    }

    @Test
    void deleteIpBlacklistTest() {
        service.addIpWhitelist(IP);
        assertThat(service.isInList(IP)).isTrue();
        service.deleteIpWhitelist(IP);
        assertThat(service.isInList(IP)).isFalse();
    }

    @Test
    void cashBlacklistTest() {
        service.addIpWhitelist(IP);
        assertThat(service.isInList(IP)).isTrue();
        service.cashWhiteList();
        assertThat(service.isInList(IP)).isTrue();

        service.deleteIpWhitelist(IP);
        assertThat(service.isInList(IP)).isFalse();
        service.cashWhiteList();
        assertThat(service.isInList(IP)).isFalse();
    }
}
