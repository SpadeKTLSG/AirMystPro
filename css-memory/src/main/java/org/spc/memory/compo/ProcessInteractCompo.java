package org.spc.memory.compo;

import org.spc.base.compo.BaseCompo;

public class ProcessInteractCompo extends BaseCompo {

    //? Default Methods

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadCompo(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }

    //! Flow

    /**
     * 进程交互
     */
    public void interact() {
        //进程交互
    }
}
