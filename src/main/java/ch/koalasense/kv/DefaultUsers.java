package ch.koalasense.kv;

import ch.koalasense.kv.data.UserEO;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class DefaultUsers {

  @Transactional
  public void loadUsers(@Observes StartupEvent evt) {
    UserEO.add("admin", "admin", "admin");
  }
}
