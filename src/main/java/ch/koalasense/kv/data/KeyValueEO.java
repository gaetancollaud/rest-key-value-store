package ch.koalasense.kv.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Optional;

@Entity
@Table(name = "t_key_value")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueEO extends PanacheEntityBase {
  @Id
  @Column(name = "key")
  public String key;

  @Column(name = "value")
  public String value;

  @Column(name = "update_time")
  public Instant updateTime;

  public static Optional<KeyValueEO> findByKey(String key) {
    return Optional.ofNullable(find("key", key).firstResult());
  }
}
