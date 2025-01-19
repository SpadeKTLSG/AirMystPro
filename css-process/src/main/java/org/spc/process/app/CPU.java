package org.spc.process.app;

import lombok.RequiredArgsConstructor;
import org.spc.base.app.BaseApp;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CPU extends BaseApp {


    public void testLoader() { //todo
        loadCompo(CPU.class, this);
    }
}
