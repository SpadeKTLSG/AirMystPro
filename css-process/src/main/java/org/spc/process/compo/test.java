package org.spc.process.compo;

import org.spc.base.compo.BaseCompo;
import org.springframework.stereotype.Component;

@Component()
public class test extends BaseCompo {
    public void doTest() {
        System.out.println("doTest");
    }
}
