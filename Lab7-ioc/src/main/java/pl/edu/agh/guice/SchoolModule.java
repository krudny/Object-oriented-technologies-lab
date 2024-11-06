package pl.edu.agh.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {

    @Provides
    public IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Named("teachersStorage")
    public String provideTeachersStorageName() {
        return "teachers2.dat";
    }

    @Provides
    @Named("classStorage")
    public String provideClassesStorageName() {
        return "classes2.dat";
    }
}
