package net.collaud.kv.api;

import net.collaud.kv.data.KeyValueEO;
import io.quarkus.panache.common.Sort;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Path("v1")
@ApplicationScoped
@Transactional
@RolesAllowed("admin")
public class KeyValueResource {

  @GET
  @Path("{namespace}")
  @Produces("text/plain")
  public String findAll(@PathParam("namespace") String namespace) {
    final List<KeyValueEO> list = KeyValueEO.findByNamespace(namespace, Sort.ascending("key"));
    return list.stream()
        .map(kv -> kv.getKey() + "=" + kv.getValue())
        .collect(Collectors.joining("\n"));
  }

  @GET
  @Path("{namespace}/{key}")
  @Produces("text/plain")
  public String getOne(@PathParam("namespace") String namespace, @PathParam("key") String key) {
    return KeyValueEO.findByKey(namespace, key)
        .map(KeyValueEO::getValue)
        .orElseThrow(() -> new NotFoundException("No value with key '" + key + "'"));
  }

  @POST
  @Path("{namespace}/{key}")
  @Produces("text/plain")
  public String save(
      @PathParam("namespace") String namespace, @PathParam("key") String key, String value) {
    final KeyValueEO kv =
        KeyValueEO.findByKey(namespace, key)
            .orElseGet(() -> KeyValueEO.builder().namespace(namespace).key(key).build());
    kv.setValue(value);
    kv.setUpdateTime(Instant.now());
    kv.persistAndFlush();
    return kv.value;
  }

  @DELETE
  @Path("{namespace}/{key}/delete")
  @Produces("text/plain")
  public String delete(@PathParam("namespace") String namespace, @PathParam("key") String key) {
    KeyValueEO.findByKey(namespace, key).ifPresent(kv -> kv.delete());
    return key;
  }

  @GET
  @Path("{namespace}/{key}/delete")
  @Produces("text/plain")
  public String deleteGet(@PathParam("namespace") String namespace, @PathParam("key") String key) {
    return delete(namespace, key);
  }

  @GET
  @Path("{namespace}/{key}/set/{value}")
  @Produces("text/plain")
  public String saveGet(
      @PathParam("namespace") String namespace,
      @PathParam("key") String key,
      @PathParam("value") String value) {
    return save(namespace, key, value);
  }
}
