package org.spc.memory.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spc.memory.entity.MemoryBlock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 内存管理器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryServiceImpl {

    /**
     * 内存块
     */
    private static final MemoryBlock[][] memory;

    /**
     * 进程状态
     */
    private static final int[][] cleanblock;


    static {
        //用64个块初始化内存，每个块可存储3个字符
        memory = new MemoryBlock[8][8];
        cleanblock = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                memory[i][j] = new MemoryBlock(); //初始化内存块为---
                cleanblock[i][j] = -1; //初始化为-1
            }
        }
    }


    /**
     * 分配内存
     *
     * @param processId 进程ID
     * @param data      数据
     * @author A
     */
    public static void allocateMemory(int processId, String data) {

        // 查找要分配的连续块
        int consecutiveBlocks = data.length() / 3 + (data.length() % 3 == 0 ? 0 : 1);
        int[] startingBlock = findConsecutiveBlocks(consecutiveBlocks);

        // 如果找到连续块，则分配内存
        if (startingBlock != null) {
            MemoryBlock[] allocatedBlocks = new MemoryBlock[consecutiveBlocks];
            int blockIndex = 0;
            for (int i = startingBlock[0]; i < startingBlock[0] + consecutiveBlocks; i++) {
                for (int j = startingBlock[1]; j < 8; j++) {

                    if (blockIndex < data.length()) {
                        int blockSize = Math.min(3, data.length() - blockIndex);
                        memory[i][j].setContent(data.substring(blockIndex, blockIndex + blockSize));
                        allocatedBlocks[blockIndex] = memory[i][j];
                        blockIndex += blockSize;
                    }
                    //跟踪内存被哪些进程所占用
                    cleanblock[i][j] = processId;
                }
            }

            System.out.println("为进程分配的内存 " + processId);
        } else {
            System.out.println("进程的内存分配失败 " + processId);
        }
    }

    /**
     * 查找连续块
     *
     * @param consecutiveBlocks 连续块
     * @return 连续块数组
     * @author A
     */
    private static int[] findConsecutiveBlocks(int consecutiveBlocks) {

        //查找并返回连续分配的起始块索引
        for (int i = 0; i < 8; i++)
            for (int j = 0; j <= 8 - consecutiveBlocks; j++)
                if (isConsecutiveBlocksAvailable(i, j, consecutiveBlocks))
                    return new int[]{i, j};

        log.warn("内存不足，无法分配连续块");
        return null;
    }

    /**
     * 检查连续块是否可用
     *
     * @param row               row
     * @param col               col
     * @param consecutiveBlocks 连续块
     * @return 是否可用
     */
    private static boolean isConsecutiveBlocksAvailable(int row, int col, int consecutiveBlocks) {
        //检查从给定索引开始的连续块是否可用
        for (int j = col; j < col + consecutiveBlocks; j++)
            if (!memory[row][j].getContent().equals("---"))
                return false;

        return true;
    }


    /**
     * 释放结束进程的内存
     *
     * @param processId 进程ID
     */
    public static void releaseMemory(int processId) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cleanblock[i][j] == processId) {
                    memory[i][j].setContent("---");
                    cleanblock[i][j] = -1;
                }
            }
        }
    }


    /**
     * 显示内存状态
     */
    public static void displayMemory() {
        int status = 0;

        //显示内存的当前状态
//        System.out.println("Memory Status:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
//                System.out.print(memory[i][j].getContent() + " ");
                if (memory[i][j].getContent().equals("---")) {
                    status++;
                }
            }
//            System.out.println();

        }
        // ? SK 暂时停止调用展示, 调试结束
//        System.out.println("空闲空间:"+status);
//        return status; //返回空闲空间
    }

    /**
     * 获取系统内存使用情况
     *
     * @return 系统内存使用情况
     */
    public static int getSystemMemoryUsage() {

        //系统内存 = 磁盘大小 + 系统分配
        List<Integer> usage = giveBlockStatus2Front(); //从文件模块获取占用表

        List<Integer> temp = new ArrayList<>(); //查表, 找到系统占用大小

        for (Integer e : usage)
            if (e == 3)
                temp.add(e);

        return temp.size();
    }

}

