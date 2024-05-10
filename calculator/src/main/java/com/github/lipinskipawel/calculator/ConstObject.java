package com.github.lipinskipawel.calculator;

import java.util.Optional;

import static java.util.Optional.empty;

public final class ConstObject {
    private final String name;
    private final Number value;
    private final Optional<ConstObject> ref;

    private ConstObject(String name, Number value, Optional<ConstObject> ref) {
        this.name = name;
        this.value = value;
        this.ref = ref;
    }

    public static ConstObject constObject(String name, Number value) {
        return new ConstObject(name, value, empty());
    }

    public static ConstObject constObject(String name, Number value, ConstObject ref) {
        return new ConstObject(name, value, Optional.of(ref));
    }

    public Optional<Number> findValue(String customObject) {
        final var names = customObject.split("\\.");
        if (!name.equals(names[0])) {
            return empty();
        }
        if (noMoreObjects(names)) {
            return Optional.of(value);
        }
        if (ref.isEmpty()) {
            return empty();
        }
        final var withoutFirstLevel = customObject.substring(customObject.indexOf('.') + 1);
        return ref.get().findValue(withoutFirstLevel);
    }

    private boolean noMoreObjects(String[] names) {
        return names.length <= 1;
    }
}
