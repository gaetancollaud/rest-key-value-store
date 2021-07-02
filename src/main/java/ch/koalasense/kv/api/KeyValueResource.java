package ch.koalasense.kv.api;

import ch.koalasense.kv.data.KeyValueEO;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/key-value")
@ApplicationScoped
@Transactional
public class KeyValueResource {

  @GET
  public String findAll() {
    final List<KeyValueEO> list = KeyValueEO.listAll(Sort.ascending("key"));
    return list.stream()
        .map(kv -> kv.getKey() + "=" + kv.getValue())
        .collect(Collectors.joining("\n"));
  }

  @GET
  @Path("{key}")
  public String getOne(@PathParam("key") String key) {
    return KeyValueEO.findByKey(key)
        .map(KeyValueEO::getValue)
        .orElseThrow(() -> new NotFoundException("No value with key '" + key + "'"));
  }

  @GET
  @Path("{key}/set/{value}")
  public String save(@PathParam("key") String key, @PathParam("value") String value) {
    final KeyValueEO kv =
        KeyValueEO.findByKey(key).orElseGet(() -> KeyValueEO.builder().key(key).build());
    kv.setValue(value);
    kv.setUpdateTime(Instant.now());
    kv.persistAndFlush();
    return kv.value;
  }
}
