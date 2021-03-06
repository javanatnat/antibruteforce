package ru.javanatnat.antibruteforce.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.javanatnat.antibruteforce.model.Ipv4Type;
import ru.javanatnat.antibruteforce.model.Whitelist;

import java.util.List;

public interface WhitelistRepository extends CrudRepository<Whitelist, Long> {
    @Modifying
    @Query("DELETE FROM whitelist w WHERE w.ip = :ip")
    void deleteByIp(@Param("ip") Ipv4Type ip);

    @Override
    List<Whitelist> findAll();
}
