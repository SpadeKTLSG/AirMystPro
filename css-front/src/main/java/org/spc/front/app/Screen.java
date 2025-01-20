package org.spc.front.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.app.BaseApp;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 显示屏前端应用
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class Screen extends BaseApp {

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
     * Screen 显示屏前端展示
     */

    public void power() throws IOException, InterruptedException {

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainGui gui = new MainGui();
            gui.showGUI();
        });
    }

}
