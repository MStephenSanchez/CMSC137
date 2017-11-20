//import java.awt.BorderLayout;
//import java.awt.Container;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.net.InetAddress;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
// 
//public class SwingPaint {
// 
//  JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn, magentaBtn;
//  DrawArea drawArea;
//  InetAddress ip;
//  Boolean isClient=false;
//  
//  public SwingPaint(InetAddress ip, Boolean isClient){
//	  this.isClient = isClient;
//	  this.ip = ip;
//  }
//  
//  ActionListener actionListener = new ActionListener() {
//	  
//  public void actionPerformed(ActionEvent e) {
//      if (e.getSource() == clearBtn) {
//        drawArea.clear();
//        try {
//    		drawArea.SendData();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//      } else if (e.getSource() == blackBtn) {
//        drawArea.black();
//      } else if (e.getSource() == blueBtn) {
//        drawArea.blue();
//      } else if (e.getSource() == greenBtn) {
//        drawArea.green();
//      } else if (e.getSource() == redBtn) {
//        drawArea.red();
//      } else if (e.getSource() == magentaBtn) {
//        drawArea.magenta();
//      }
//    }
//  };
// 
//  public void setDatagramServer(DatagramServer ds){
//	  drawArea.ds = ds;
//  }
//  
//  public void show() {
//    // create main frame
//    JFrame frame = new JFrame("Swing Paint");
//    Container content = frame.getContentPane();
//    // set layout on content pane
//    content.setLayout(new BorderLayout());
//    // create draw area
//    drawArea = new DrawArea(ip, isClient);
// 
//    // add to content pane
//    content.add(drawArea, BorderLayout.CENTER);
// 
//    // create controls to apply colors and call clear feature
//    JPanel controls = new JPanel();
//  
//    //Insert Commands
//    clearBtn = new JButton("Clear");
//    clearBtn.addActionListener(actionListener);
//    blackBtn = new JButton("Black");
//    blackBtn.addActionListener(actionListener);
//    blueBtn = new JButton("Blue");
//    blueBtn.addActionListener(actionListener);
//    greenBtn = new JButton("Green");
//    greenBtn.addActionListener(actionListener);
//    redBtn = new JButton("Red");
//    redBtn.addActionListener(actionListener);
//    magentaBtn = new JButton("Magenta");
//    magentaBtn.addActionListener(actionListener);
// 
//    // add to panel
//    controls.add(greenBtn);
//    controls.add(blueBtn);
//    controls.add(blackBtn);
//    controls.add(redBtn);
//    controls.add(magentaBtn);
//    controls.add(clearBtn);
// 
//    // add to content pane
//    content.add(controls, BorderLayout.NORTH);
// 
//    frame.setSize(600, 600);
//    // can close frame
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    // show the swing paint result
//    frame.setVisible(true);
// 
//    // Now you can try our Swing Paint !!! Enjoy <img draggable="false" class="emoji" alt="😀" src="https://s.w.org/images/core/emoji/2.3/svg/1f600.svg">
//  }
// 
//}