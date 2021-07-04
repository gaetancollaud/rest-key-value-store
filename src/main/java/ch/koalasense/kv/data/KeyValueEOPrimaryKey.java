package ch.koalasense.kv.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyValueEOPrimaryKey implements Serializable {

  public String namespace;

  public String key;
}
