package org.spc.file.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.app.BaseApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.spc.base.common.constant.FileCT.ROOT_AUTH;

/**
 * 文件模块总应用
 */
@Service
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class FileApp extends BaseApp {

    @Autowired
    FileSyS fileSyS;

    @Autowired
    DiskSyS diskSyS;

    //All Compos


    //? Default Methods

    /**
     * 初始化系统成员 + 读取磁盘内容
     */
    public FileApp() {
        log.debug("磁盘模块开机中...");
        // Spring IOC
        log.debug("磁盘模块成员初始化完成");
        diskSyS.normalRebootDisk();
        log.debug("文件模块开机中...");
        // Spring IOC
        log.debug("文件模块成员初始化完成");
        fileSyS.normalRebootFile();
        log.debug("文件模块重读完成");
        diskSyS.getHandleDiskCompo().mountDefaultDir2BLOCKS();
        log.debug("系统默认目录挂载完成");
        reload();
        log.info("文件系统开机完成 -by SpadeKTLSG-");
    }

    /**
     * 系统特殊操作
     *
     * @param auth 权限
     * @param type 操作类型
     */
    public FileApp(String auth, Integer type) {
        if (auth.equals(ROOT_AUTH)) { //root权限执行
            log.debug("警告, 正在使用root权限执行系统操作");
            if (type.equals(1)) {
                log.info("///格式化磁盘 + 重建索引///");
                kickDiskRoboot();
                reboot();
            } else if (type.equals(2)) {
                log.info("///覆盖磁盘 + 重建索引///");
                coverDiskRoboot();
                reboot();
            } else if (type.equals(3)) {
                log.info("///摧毁系统, 世界毁灭吧///");
                fileSyS = null;
                diskSyS = null;

            } else {
                log.info("宁的操作不在系统操作范围内");
            }
        } else {
            log.info("宁的权限不足. 对不起, 做不到");
        }
    }

    /**
     * 新增文件系统的一个对象
     *
     * @param A 文件/文件夹对象
     */
    public void addContent(Object A) {
        fileSyS.addContentFS(A);
        diskSyS.addContentDS(A);
    }


    //! Flow

    /**
     * 删除文件系统的一个对象
     *
     * @param A 文件/文件夹对象
     */
    public void deleteContent(Object A) {
        fileSyS.deleteContentFS(A);
        diskSyS.deleteContentDS(A);

    }


    //! 1. 系统操作

    /**
     * 修改文件系统的一个对象
     *
     * @param A 被修改的对象
     * @param B 修改后的对象
     */
    public void alterContent(Object A, Object B) {
        fileSyS.alterContentFS(A, B);
        diskSyS.alterContentDS(A, B);
    }

    /**
     * 查找文件系统的一个对象
     *
     * @param A 文件/文件夹对象
     * @return 拷贝的镜像对象
     */
    public Object selectContent(Object A) {

        return fileSyS.selectContentFS(A);
        //selectContentDS(A); //无需关注底层磁盘实现
    }

    //! 3. 实用工具
    //清理回收站
    public void cleanRecycleBin() {
        //删除/boot下的所有树节点文件, 但是不回收盘块(模拟电脑被垃圾堆满的效果)
        fileSyS.cleanRebootFile();
    }

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

    /**
     * FileApp通电
     */
    public void power() throws IOException, InterruptedException {
        //文件不需要主动保持运行, 核心在CPU那里
    }

    /**
     * 重启文件系统
     */
    public void reboot() {
        log.info("文件系统重启中");
        new FileApp();
    }

    /**
     * 刷新
     * <p>重新从磁盘加载资源</p>
     */
    public void reload() {
        log.info("刷新中...正在重新从磁盘加载系统内容");
        diskSyS.normalRebootDisk();
        log.info("刷新中...正在重建文件系统索引");
        fileSyS.normalRebootFile();
    }


    //! 2. 系统功能接口实现 - CRUD
    //API传递使用Object充当DTO, 只赋值PathName

    /**
     * 完整展示系统状态
     */
    public void state() {
        log.info("磁盘模块-文件模块状态展示");
        //1. 磁盘系统
        System.out.println(diskSyS);
        //2. 文件系统
        System.out.println(fileSyS);
    }

    /**
     * 展示磁盘模块状态
     */
    public void stateDisk() {
        log.info("磁盘模块状态展示");
        //?性能指标
        System.out.println(diskSyS);
    }

    /**
     * 展示文件模块状态
     */
    public void stateFile() {
        log.info("文件模块状态展示");
        //?性能指标
        System.out.println(fileSyS);
    }

    /**
     * 覆盖模式启动
     */
    public void coverDiskRoboot() {
        diskSyS.coverRebootDisk();
        fileSyS.coverRebootFile();
        //这边必须要重建索引, 否则文件系统就真的"DOWN"了
        fileSyS.normalRebootFile();
        diskSyS.getHandleDiskCompo().mountDefaultDir2BLOCKS();
    }

    /**
     * 格式化模式启动
     */
    public void kickDiskRoboot() {
        diskSyS.cleanRebootDisk();
        fileSyS.cleanRebootFile();
        //这边必须要重建索引, 否则文件系统就真的"DOWN"了
        fileSyS.normalRebootFile();
        diskSyS.getHandleDiskCompo().mountDefaultDir2BLOCKS();
    }


}
