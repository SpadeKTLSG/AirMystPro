package org.spc.file.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.client.FrontClient;
import org.spc.base.common.exception.FailedException;
import org.spc.base.entity.file.dir;
import org.spc.base.entity.file.file;
import org.spc.base.sys.compo.BaseCompo;
import org.spc.file.app.FileApp;
import org.spc.file.app.FileSyS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.spc.base.common.constant.FileCT.*;

/**
 * 与前端交互组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class WithFrontCompo extends BaseCompo {

    @Autowired
    FrontClient frontClient;

    @Autowired
    HandleDiskCompo handleDiskCompo;

    @Autowired
    WithProcessCompo withProcessCompo;

    @Autowired
    FileSyS fileSyS;

    @Autowired
    FileApp fileApp;

    //? Artifacts


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


    //! 1.信息传递

    /**
     * 获取全局磁盘使用量
     *
     * @return 全局磁盘使用量(百分数
     */
    public Double diskUsageAmount_All() {
        double count;
        List<Integer> usedFATList = handleDiskCompo.getFATOrder();
        if (usedFATList != null) {
            count = ((usedFATList.size() + 1.0) / (double) DISK_SIZE);
        } else {
            count = 0.0;
        }

        double or = count * 100;

        or = (double) Math.round(or * 100) / 100;       //保留两位
        log.debug("全局磁盘使用量 = {} %", or);
        return or;
    }

    /**
     * 传递路径给前端
     * <p>[/, /:home, /:app, /:tmp, /:conf,.....]</p>
     *
     * @return String[] pathList
     */
    public String[] givePath2Front() {
        //?foreach 读取PM内的项目, 打包到String[]

        String[] pathList = new String[fileSyS.pathManager.size()];

        //从PM中选取所有非""的项目, 封装到String[]中, 传递给Front
        String[] finalPathList = pathList;
        fileSyS.pathManager.forEach((k, v) -> {
            if (!v.equals(" ")) finalPathList[Integer.parseInt(String.valueOf(k))] = v;
        });


        pathList = Arrays.stream(pathList)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        //?刷洗符合要求
        for (int i = 0; i < pathList.length; i++) {
            String e = pathList[i];
            if (e.equals("/")) {
                pathList[i] = "";
            } else if (e.startsWith("/")) {
                pathList[i] = e.substring(1);
            }
            if (e.contains(":")) {
                if (Objects.equals(e.split((":"))[0], "/"))
                    pathList[i] = e.replace(":", "");
                else {
                    pathList[i] = e.replace(":", "/");
                }
            }
        }

        //刷洗空值
        pathList = Arrays.stream(pathList)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);


        return pathList;
    }


    /**
     * 传递磁盘占用情况给前端
     *
     * @return DTO FAT
     */
    public List<Integer> giveBlockStatus2Front() {
        List<Integer> greatFAT = handleDiskCompo.mergeFATs();
        //刷洗greatFAT, 0: 空闲 1:占用 3: 系统-> DTO FAT
        //?刷洗符合要求
        for (int i = 0; i < greatFAT.size(); i++) {
            Integer e = greatFAT.get(i);
            if (e.equals(Null_Pointer)) {
                greatFAT.set(i, 0);
            } else {
                greatFAT.set(i, 1);
            }
        }
        //设置系统占用为3
        greatFAT.set(0, 3);
        greatFAT.set(1, 3);
        greatFAT.set(2, 3);
        return greatFAT;
    }

    //! 2. 请求处理

    /**
     * 从前端接收完整请求
     *
     * @param order 前端完整请求
     */
    public void getFrontRequest(String order) {
        withProcessCompo.isExeFile = 0; //复位可执行标志位
        withProcessCompo.handleCommon(handleOrder(order));
    }


    /**
     * 处理前端请求
     *
     * @param allOrder 完整前端请求
     * @return 返回处理后的对象用于进程展示
     */
    public Object handleOrder(String allOrder) {

        String[] orderList = allOrder.split(" ");
        int order_size = orderList.length;
        String order = null, allName = null, path1 = null, path2 = null;

        switch (order_size) {
            case 1, 2 -> {
                throw new FailedException("找不到对应的命令");

            }
            case 3 -> { //标准三个参数 -> order | 文件全名 | path1(ELSE)
                order = orderList[0];
                allName = orderList[1];
                path1 = orderList[2];
            }
            case 4 -> { //标准四个参数 -> order | 文件全名 | path1 | path2(ELSE)
                order = orderList[0];
                allName = orderList[1];
                path1 = orderList[2];
                path2 = orderList[3];
            }
            default -> {
                log.warn("命令错误");
                throw new FailedException("找不到对应的命令");
            }
        }

        if (order == null | allName == null | path1 == null) {
            log.warn("命令错误");
            throw new FailedException("找不到对应的命令");
        }

        return doFunction(order, allName, path1, path2);          //?返回传递进程对象信息
    }


    /**
     * 执行对应命令, 动态赋值
     *
     * @param order   命令
     * @param allName 文件全名
     * @param path    文件路径
     * @param subPath 子路径
     * @return 返回工作对象给进程
     */
    public Object doFunction(String order, String allName, String path, String subPath) {
        switch (order) { //根据order调用对应的方法, 根据命令的不同传递参数
            case "create" -> {
                return createOrder(packageObjectfrFront(order, allName, path));
            }
            case "copy" -> {
                return copyOrder(selectObjectfrFront(order, allName, path), subPath);
            }
            case "delete" -> {
                return deleteOrder(selectObjectfrFront(order, allName, path));
            }
            case "move" -> {
                return moveOrder(selectObjectfrFront(order, allName, path), subPath);
            }
            case "type" -> {
                return typeOrder(selectObjectfrFront(order, allName, path));
            }
            case "change" -> {
                return changeOrder(selectObjectfrFront(order, allName, path), subPath);
            }
            case "makdir" -> {
                return makdirOrder(packageObjectfrFront(order, allName, path));
            }
            case "chadir" -> {
                return chadirOrder(selectObjectfrFront(order, allName, path), subPath);
            }
            case "deldir" -> {
                return deldirOrder(selectObjectfrFront(order, allName, path));
            }
            case "run" -> {
                return runOrder(selectObjectfrFront(order, allName, path));
            }
            case "edit" -> {
                return editOrder(selectObjectfrFront(order, allName, path), subPath); //真正的用户修改内容指令
            }
            default -> {
                log.error("命令错误");
                throw new FailedException("找不到对应的命令");
            }
        }
    }


    //? 两种不同的对象处理方式, 一种是定位到现有对象, 一种是制作新对象

    /**
     * 定位到现有对象完成DTO传递
     *
     * @param order   操作指令
     * @param allName 文件全名
     * @param path    文件路径
     * @return 打包对象
     */
    public Object selectObjectfrFront(String order, String allName, String path) {//定位到现有对象, 需要根据操作order判断文件还是文件夹
        try {
            if (order.equals("create") | order.equals("copy") | order.equals("delete") | order.equals("move") | order.equals("type") | order.equals("change") | order.equals("run") | order.equals("edit")) {
                String pathName = path + ':' + allName.split("\\.")[0];
                String extendName = '.' + allName.split("\\.")[1];

                file temp_file = new file(pathName, extendName, "");
                return fileApp.selectContent(temp_file);

            } else if (order.equals("makdir") | order.equals("chadir") | order.equals("deldir")) {
                String pathName = path + ':' + allName.split("\\.")[0];
                String extendName = ".";

                dir temp_dir = new dir(pathName, extendName);
                return fileApp.selectContent(temp_dir);

            } else {

                throw new FailedException("命令语法错误!");
            }
        } catch (Exception e) {
            throw new FailedException("命令语法错误!");
        }

    }

    /**
     * 制作新对象完成DTO传递
     *
     * @param order   操作指令
     * @param allName 文件全名
     * @param path    文件路径
     * @return 新对象
     */
    public Object packageObjectfrFront(String order, String allName, String path) {//制作新对象对象, 需要根据操作order判断文件还是文件夹
        if (order.equals("create") | order.equals("copy") | order.equals("delete") | order.equals("move") | order.equals("type") | order.equals("change") | order.equals("run") | order.equals("edit")) {
            file temp_file = new file(path + ':' + allName.split("\\.")[0], '.' + allName.split("\\.")[1], "");
            fileApp.addContent(temp_file);
            return fileApp.selectContent(temp_file);

        } else if (order.equals("makdir") | order.equals("chadir") | order.equals("deldir")) {
            dir temp_dir = new dir(path + ':' + allName.split("\\.")[0], "");
            fileApp.addContent(temp_dir);
            return fileApp.selectContent(temp_dir);

        } else {
            throw new FailedException("命令语法错误!");
        }
    }

    //! 以下是具体的命令实现, 每个方法注释后面标记了前端传递参数的数量 - 3 / 4, 以及对应的参数类型

    /**
     * 创建文件
     * <p>create XXX.XXX /tmp</p>
     *
     * @param object 源文件对象
     * @return 返回工作对象给进程
     */
    public Object createOrder(Object object) {
        return object;
    }

    /**
     * 创建文件夹
     * <p>makdir XXX.XXX /tmp </p>
     *
     * @param object 文件夹对象
     */
    public Object makdirOrder(Object object) {
        return object;
    }


    /**
     * 复制文件 - 4
     * <p>copy XXXA.XXX /tmp /home</p>
     *
     * @param A       源文件对象
     * @param subPath 目标位置
     */
    public Object copyOrder(Object A, String subPath) {
        if (A == null) return null;
        if (subPath == null) return null;
        file or_file = (file) A;
        file B = new file(subPath + ':' + or_file.fcb.pathName.split(":")[1], or_file.fcb.getExtendName(), or_file.getContent());
        fileApp.addContent(B);
        return B;
    }


    /**
     * 删除文件
     * <p>delete XXX.XXX /tmp</p>
     *
     * @param object 文件对象
     */
    public Object deleteOrder(Object object) {
        if (object == null) return null;
        fileApp.deleteContent(object);
        return object;
    }


    /**
     * 删除文件夹
     * <p>deldir XXX /tmp</p>
     *
     * @param object 文件夹对象
     */
    public Object deldirOrder(Object object) {
        if (object == null) return null;
        fileApp.deleteContent(object);
        return object;
    }


    /**
     * 移动文件
     * <p>move XXX.XXX /tmp /home</p>
     *
     * @param A 源文件对象
     */
    public Object moveOrder(Object A, String subPath) {
        if (A == null) return null;
        if (subPath == null) return null;
        file or_file = (file) A;
        file B = new file(subPath + ':' + or_file.fcb.pathName.split(":")[1], or_file.fcb.getExtendName(), or_file.getContent());
        fileApp.alterContent(A, B);
        return B;
    }


    /**
     * 展示文件, 使用msg传递到前端
     * <p>type XXX.XXX /tmp</p>
     *
     * @param object 文件对象
     */
    public Object typeOrder(Object object) {
        if (object == null) return null;
        frontClient.sendException(((file) object).getContent());
        return object;
    }


    /**
     * 修改文件名称
     * <p>change XXX.XXX /tmp YYY</p>
     *
     * @param A       文件对象
     * @param newName 新名称
     */
    public Object changeOrder(Object A, String newName) {
        if (A == null) return null;
        file or_file = (file) A;
        if (newName == null) newName = FILE_NAME_DEFAULT; //容错判定
        file B = new file(or_file.fcb.pathName.split(":")[0] + ':' + newName, or_file.fcb.getExtendName(), or_file.getContent());
        fileApp.alterContent(A, B);
        return B;
    }


    /**
     * 修改文件夹名称
     * <p>chadir XXX.XXX /tmp YYY</p>
     */
    public Object chadirOrder(Object A, String newName) {
        if (A == null) return null;
        dir or_file = (dir) A;
        if (newName == null) newName = DIR_NAME_DEFAULT; //容错判定
        dir B = new dir(or_file.fcb.pathName.split(":")[0] + ':' + newName, or_file.fcb.getExtendName());
        fileApp.alterContent(A, B);
        return B;
    }


    /**
     * 运行可执行文件
     * <p>exefile XXX.XXX /tmp</p>
     *
     * @param object 文件对象
     * @return 返回工作对象给进程
     */
    public Object runOrder(Object object) {
        if (object == null) return null;
        //判断是否符合命名规范; #开头
        file temp_file = (file) object;
        if (!temp_file.getFcb().pathName.split(":")[1].startsWith("#")) {
            throw new FailedException("不是可执行文件");
        }
        withProcessCompo.isExeFile = 1;//标记为可执行文件

        return object;
    }


    /**
     * 修改文件内容
     * <p>edit XXX.XXX asdasdasdasd</p>
     *
     * @return 返回工作对象给进程
     */
    public Object editOrder(Object A, String newContent) {
        if (A == null) return null;
        if (newContent == null) newContent = ""; //容错判定
        file or_file = (file) A;
        file B = new file(or_file.fcb.pathName.split(":")[0] + ':' + or_file.fcb.pathName.split(":")[1], or_file.fcb.getExtendName(), newContent);
        fileApp.alterContent(A, B);

        return B;
    }


}
