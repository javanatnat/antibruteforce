package ru.javanatnat.antibruteforce.service;

public interface WhitelistService {
    void addIpWhitelist(String ip);
    void deleteIpWhitelist(String ip);
    boolean isInList(String ip);
    void cashWhiteList();
}
