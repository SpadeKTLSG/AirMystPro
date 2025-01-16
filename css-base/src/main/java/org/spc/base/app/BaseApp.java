package org.spc.base.app;

import org.spc.base.artifact.BaseArtifact;
import org.spc.base.compo.BaseCompo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class BaseApp {

    /**
     * 组件组
     */
    List<BaseCompo> compoGroup;

    BaseCompo baseCompo;


    public void loadCompo() {
        //load
        this.compoGroup.addAll()

        //init
        baseCompo.initial();
    }


}
