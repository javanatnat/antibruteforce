package ru.javanatnat.antibruteforce.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.javanatnat.antibruteforce.config.DataJdbcConfig;
import ru.javanatnat.antibruteforce.model.Blacklist;
import ru.javanatnat.antibruteforce.model.Ipv4Type;
import ru.javanatnat.antibruteforce.service.ListsPostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataJdbcConfig.class)
public class BlacklistRepositoryTest {

    @Container
    private static final PostgreSQLContainer<ListsPostgreSQLContainer> postgresqlContainer
            = ListsPostgreSQLContainer.getInstance();

    private static final String IP = "128.11.0.1";

    @Autowired
    BlacklistRepository repository;

    @Test
    void findAllTest() {
        assertThat(repository.findAll().size()).isEqualTo(0);
        repository.save(new Blacklist(IP));
        assertThat(repository.findAll().size()).isEqualTo(1);
        repository.save(new Blacklist(IP));
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    void deleteByIpTest() {
        repository.deleteAll();
        repository.save(new Blacklist(IP));
        assertThat(repository.findAll().size()).isEqualTo(1);
        repository.deleteByIp(new Ipv4Type(IP));
        assertThat(repository.findAll().size()).isEqualTo(0);
    }
}
