package org.spc.process.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.compo.BaseCompo;
import org.spc.process.entity.Process;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class IOHandlerCompo extends BaseCompo {

    //? Default Methods

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadArtifact(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }

    @Transactional
    public void write2File(Process process) {
        //获取线程寄存器的迭代器
        Iterator<Map.Entry<String, Integer>> iterator = process.getPcb().getRegister().entrySet().iterator();


        try (FileWriter fileWriter = new FileWriter("src/main/resources/static/out.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            while (iterator.hasNext()) {
                Map.Entry<String, Integer> next = iterator.next();
                bufferedWriter.write(next.getKey() + "=" + next.getValue() + "\t\n"); //写入文件
            }

        } catch (IOException e) {
            log.error("写入文件异常", e);
        }
    }
}
