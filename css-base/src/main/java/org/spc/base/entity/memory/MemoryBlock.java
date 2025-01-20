package org.spc.base.entity.memory;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 内存块
 */
@Data
@Builder
@Accessors(chain = true)
public class MemoryBlock {

    /**
     * 内存块的内容, 简单设计为字符串
     */
    private String content;

    /**
     * 空块的默认内容 ---
     */
    public MemoryBlock() {
        this.content = "---";
    }

}
