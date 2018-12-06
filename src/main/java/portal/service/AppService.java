package portal.service;

import java.util.List;

import portal.entity.Application;
import portal.models.App;

public interface AppService {
    Application addApplication(Application application);
    void delete(Application application);
    List<Application> getAll();
    List<App> getAllApp();
    Application getByName(String AppName);
    Application updateApp(Application application);
}
