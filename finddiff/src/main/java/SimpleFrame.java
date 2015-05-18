
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class SimpleFrame extends JFrame implements KeyListener{
    private JTextArea text=new JTextArea();
    public SimpleFrame(){
        super.setTitle("Welcome！");
        JScrollPane scr=new JScrollPane(text);
        scr.setBounds(5, 5, 5, 5);
        super.add(scr);
        text.addKeyListener(this);
        super.setSize(310,210);
        super.setVisible(true);
        super.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent arg0){
                System.exit(1);
            }
        });
        new MatchPictureThread().start();
        new StartGameThread().start();
        new MonitorThread().start();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        text.append("输入的内容是:"+e.getKeyChar()+"\n");
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
    	//a
    	if (e.getKeyCode() == 65) {
//    		new MatchPictureThread().start();
    	}
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        text.append("键盘“"+KeyEvent.getKeyText(e.getKeyCode())+"”键松开\n");
    }
}