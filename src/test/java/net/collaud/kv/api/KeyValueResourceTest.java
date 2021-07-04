package net.collaud.kv.api;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import net.collaud.kv.PostgresqlResource;
import net.collaud.kv.data.KeyValueEO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.time.Instant;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(PostgresqlResource.class)
@Transactional
class KeyValueResourceTest {

  @AfterEach
  void afterEach() {
    KeyValueEO.deleteAll();
  }

  @Test
  void testSecurity() {
    insertRandomValue("namespace1", "key1");

    // no auth
    when().get("/v1/namespace1").then().statusCode(401);

    // bad auth
    given().auth().basic("admin", "asdfsdf").when().get("/v1/namespace1").then().statusCode(401);

    // good auth
    given().auth().basic("admin", "admin").when().get("/v1/namespace1").then().statusCode(200);
  }

  @Test
  void testGetFromNamespace() {
    given()
        .auth()
        .basic("admin", "admin")
        .when()
        .get("/v1/my-namespace")
        .then()
        .assertThat()
        .statusCode(200)
        .body(is(""));
  }

  void insertRandomValue(String namespace, String key) {
    insertValue(namespace, key, "random");
  }

  void insertValue(String namespace, String key, String value) {
    KeyValueEO.builder()
        .namespace(namespace)
        .key(key)
        .value(value)
        .updateTime(Instant.now())
        .build()
        .persist();
  }
}
