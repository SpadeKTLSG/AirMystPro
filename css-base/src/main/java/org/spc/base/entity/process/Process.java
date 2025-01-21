package org.spc.base.entity.process;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.spc.base.entity.process.struct.Pcb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 进程实体
 */

@Data
@Builder
@Accessors(chain = true)
public class Process {

    /**
     * 文件读取
     */
    public FileReader file;
    /**
     * 文件读取
     */
    public BufferedReader bufferedReader;
    /**
     * 是否停止
     * volatile 保证可见性
     */
    private volatile boolean stop;
    /**
     * 进程控制块
     */
    private Pcb pcb;


    public Process(boolean stop, Pcb pcb) {
        this.stop = stop;
        this.pcb = pcb;
        file = null;
        bufferedReader = null;
    }


    public Process(String fileName) throws IOException {
        pcb = new Pcb();
        file = new FileReader(fileName);
        bufferedReader = new BufferedReader(file);
    }

}
