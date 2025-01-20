package org.spc.file.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.app.BaseApp;
import org.spc.base.entity.file.TREE;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


/**
 * 文件应用
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class FileSyS extends BaseApp {


    /**
     * 文件系统树形结构
     * <p>没有存在磁盘里</p>
     */
    public TREE tree;

    /**
     * 路径管理器
     * <p>没有存在磁盘里</p>
     */
    public Map<Integer, String> pathManager;

    /**
     * 扩展名管理器
     * <p>没有存在磁盘里</p>
     */
    public Map<Integer, String> extendManager;


    //All Compos


    //? Default Methods

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

        //通电
        try {
            this.power();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadCompo(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }


    //! Flow

    /**
     * FileSyS通电
     */
    public void power() throws IOException, InterruptedException {
        //文件不需要主动保持运行, 核心在CPU那里
    }
}
