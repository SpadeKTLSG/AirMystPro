package org.spc.base.sys.load;

import java.util.List;

/**
 * 加载器
 */
public interface Loader<T> {

    List<T> load(Class<?> clazz, Object instance);

}
