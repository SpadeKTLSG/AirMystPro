package org.spc.base.sys.loader;

import java.util.List;

public interface Loader<T> {

    List<T> load(Class<?> clazz, Object instance);
}
