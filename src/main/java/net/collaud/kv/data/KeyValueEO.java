package net.collaud.kv.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "t_key_value")
@IdClass(KeyValueEOPrimaryKey.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueEO extends PanacheEntityBase {

  @Id
  @Column(name = "namespace")
  public String namespace;

  @Id
  @Column(name = "key")
  public String key;

  @Column(name = "value")
  public String value;

  @Column(name = "update_time")
  public Instant updateTime;

  public static Optional<KeyValueEO> findByKey(String namespace, String key) {
    return Optional.ofNullable(find("namespace= ?1 AND key=?2", namespace, key).firstResult());
  }

  public static List<KeyValueEO> findByNamespace(String namespace, Sort sort) {
    return find("namespace", sort, namespace).list();
  }
}
