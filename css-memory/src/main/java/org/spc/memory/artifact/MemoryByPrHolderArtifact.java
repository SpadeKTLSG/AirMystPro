package org.spc.memory.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.artifact.BaseArtifact;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 内存-进程持有工件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryByPrHolderArtifact extends BaseArtifact {


    /**
     * 内存-进程持有状态
     */
    int[][] cleanblock;

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

        cleanblock = new int[8][8];
        Arrays.stream(cleanblock).forEach(
                row -> Arrays.fill(row, -1)
        );
    }

    @Override
    public void loadConfig() {

    }
}
