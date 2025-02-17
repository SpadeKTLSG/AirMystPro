package org.spc.front.compo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 弹出对话框
 */
public class PopupDialog extends JDialog {

    /**
     * 文本标签
     */
    private final JLabel textLabel;


    public PopupDialog(JFrame parent) {
        super(parent, "提示", true);

        textLabel = new JLabel();

        textLabel.setFont(new Font("Bold", Font.PLAIN, 20)); //字体样式 : 加粗 , 大小 30
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);        //居中显示


        JButton okButton = new JButton("OK");

        okButton.setFont(new Font("Bold", Font.PLAIN, 30));
        okButton.setHorizontalAlignment(SwingConstants.CENTER);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(textLabel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
        setSize(400, 300);

        setLocationRelativeTo(null);        //居中显示
    }

    public void setText(String text) {
        textLabel.setText(text);
    }
}
