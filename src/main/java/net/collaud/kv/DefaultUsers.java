package net.collaud.kv;

import net.collaud.kv.data.UserEO;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class DefaultUsers {

  @ConfigProperty(name = "users.default.username", defaultValue = "admin")
  String defaultUsername;

  @ConfigProperty(name = "users.default.password", defaultValue = "admin")
  String defaultPassword;

  @Transactional
  public void createUsers(@Observes StartupEvent evt) {
    if (UserEO.find("username", defaultUsername).firstResult() == null) {
      UserEO.add(defaultUsername, defaultPassword, "admin");
    }
  }
}
