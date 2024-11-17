package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {

    @Provides
    public IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }
}
