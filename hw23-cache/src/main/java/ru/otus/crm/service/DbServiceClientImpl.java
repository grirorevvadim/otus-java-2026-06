package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> cache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, HwCache<String, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        var saved = transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                return clientDataTemplate.insert(session, clientCloned);
            }
            return clientDataTemplate.update(session, clientCloned);
        });
        cache.put(String.valueOf(saved.getId()), saved.clone());
        log.info("saved client: {}", saved);
        return saved;
    }

    @Override
    public Optional<Client> getClient(long id) {
        var key = String.valueOf(id);
        var cached = cache.get(key);
        if (cached != null) {
            return Optional.of(cached.clone());
        }
        Optional<Client> result = transactionManager.doInReadOnlyTransaction(session -> {
            log.info("get info from db");
            return clientDataTemplate.findById(session, id);
        });
        result.ifPresent(c -> cache.put(key, c.clone()));
        return result;
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
