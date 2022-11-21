package backend.struct;


import java.io.Serial;
import java.io.Serializable;

import static backend.constants.FileConfig.SERIAL_VERSION_UID;

public record Pair<K, V>(K key, V value) implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

}
