import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class Information extends JPanel {
	JLabel RemainTime, PlayTime;
	JButton Startbu;
	JButton Endbu;
	JButton Returnbu;
	JPanel jp2;
	JButton HansuBack;
	JToggleButton[] Player;
	PlayTimer PlayTimer;
	RemainTimer RemainTimer;
	JPanel[] PlayerPanel;
      JLabel lUserName;
	int hours, mins, secs, hours1, mins1, secs1;
	double oo, xx, oo1, xx1;
	boolean loop, first = true, TurnIsChange = false;
	ImageIcon icon;

	boolean IsStart;
	void setTurnIsChangeToTrue() {// TurnIsChange�� True�� ����.���̹ٲ�� �����ð� �ʱ�ȭ�� ���ؼ�
		TurnIsChange = true;
		SetTurn();
	}
	void SetTurn(){//���� �ٲ�� ��۹�ư�� �ٲ��
		if(Player[0].isSelected()){
			Player[0].setSelected(false);
			Player[1].setSelected(true);
		}
		else{
			Player[1].setSelected(false);
			Player[0].setSelected(true);
		}
	}
	void ResetTimer(){
		PlayTime.setText("00:00:00");
		RemainTime.setText("00:00:00");
		HansuBack.setVisible(false);
		Startbu.setVisible(true);
		PlayTimer.suspend();
		RemainTimer.suspend();
		IsStart=false;
	}
	
	public Information() {
		setLayout(null);
		icon = new ImageIcon("Images/board.png");
		jp2 = new JPanel();
		jp2.setLayout(null);
		add(jp2);
		TitledBorder TB = new TitledBorder(new LineBorder(Color.black));
		Font font = new Font("Arial", Font.ITALIC, 20);
		RemainTime = new JLabel(); //���� �ð� ����
		RemainTime.setBounds(0, 0, 200, 100);
		RemainTime.setHorizontalAlignment(SwingConstants.CENTER);
		RemainTime.setText("00:00:00");
		RemainTime.setBorder(TB);
		RemainTime.setFont(font);
		PlayTime = new JLabel(){
			public void paintComponents(Graphics g){
				//super.paintComponents(g);
				g.drawImage(icon.getImage(), 0, 0, 100, 80, null);
			}
		};
		PlayTime.setBounds(0, 100, 200, 80); //���� �ѽð� ����
		PlayTime.setHorizontalAlignment(SwingConstants.CENTER);
		PlayTime.setText("00:00:00");
		PlayTime.setBorder(TB);
		PlayTime.setFont(font);

		HansuBack = new JButton();
		HansuBack.setBounds(0, 460, 100, 130);
		HansuBack.setVisible(false);
		HansuBack.setText("�ϳѱ��");
		HansuBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(JavaObjClientView.turn==JavaObjClientView.myTurn) {
					if(JavaObjClientView.turn!=2)
				JavaObjClientView.SendTurn();
				}
			}
		});

		Player = new JToggleButton[2];
		for (int i = 0; i < 2; i++) {
			Player[i] = new JToggleButton();
			Player[i].setText("Player " + (i + 1));
			Player[i].setBorder(TB);
			Player[i].setBounds(100 * i, 180, 100, 30);
			Player[i].setEnabled(false);
			add(Player[i]);
		}
		Player[0].setSelected(true);

		PlayerPanel = new JPanel[2];
		for (int i = 0; i < 2; i++) {
			PlayerPanel[i] = new JPanel();
			PlayerPanel[i].setLayout(new FlowLayout());
			PlayerPanel[i].setBounds(100 * i, 210, 100, 250);
			PlayerPanel[i].setBorder(TB);
			add(PlayerPanel[i]);
		}

		Startbu = new JButton();
		Startbu.setBounds(0, 460, 100, 110);
		Startbu.setText("����");
		Startbu.addMouseListener(new ButtonMouseListener());
		Endbu = new JButton();
		Endbu.setBounds(100, 460, 100, 110);
		Endbu.setText("������");
		Endbu.addMouseListener(new ButtonMouseListener());
		Returnbu = new JButton();
		Returnbu.setBounds(0, 570, 200, 120);
		Returnbu.setText("������");
		Returnbu.addMouseListener(new ButtonMouseListener());
	
		
		add(RemainTime);
		add(PlayTime);
		add(HansuBack);
		add(Startbu);
		add(Endbu);
		add(Returnbu);

		setFocusable(false);
		setLocation(655, 0);
		setSize(200, 730);
		setVisible(true);
		PlayTimer = new PlayTimer();
		RemainTimer = new RemainTimer();

	}

	class PlayTimer extends Thread {
		void timer1() {// �÷��� �ð�
			xx = (System.currentTimeMillis() - oo) / 1000d;
			hours = ((int) xx % 86400) / 3600;
			mins = ((int) xx % 3600) / 60;
			secs = (int) xx % 60;
			PlayTime.setText(String.format("%02d:%02d:%02d  ", hours, mins,
					secs));
			//le.repaint();
		}

		public void run() {
			// while (true) {
			while (true) {
				try {
					if (loop) {
						Thread.sleep(100);
						timer1();
					} else {
						Thread.sleep(1000 * 60 * 60 * 24); // 24�ð� ��������
					}
				} catch (InterruptedException e) {
					break;
				}
			}
			// }
		}
	}

	class RemainTimer extends Thread {
		void timer() {// �����ð�
			xx1 = (oo1 + (30000d) - System.currentTimeMillis()) / 1000d; //30000d�� 30�� �ð� ����
			hours1 = ((int) xx1 % 86400) / 3600;
			mins1 = ((int) xx1 % 3600) / 60;
			secs1 = (int) xx1 % 60;
			RemainTime.setText(String.format("%02d:%02d:%02d  ", hours1, mins1,
					secs1));
			// ���� �ٲ���ų� �ð��� �ʰ�������
			if (oo1 + (30000d) - System.currentTimeMillis() <= 0d
					|| TurnIsChange) {
				if (oo1 + (30000d) - System.currentTimeMillis() <= 0d)// ���� ���°�
																		// �ð��ʰ����
					JavaObjClientView.changeTurn();// ���� �ٲ۴�
				oo1 = System.currentTimeMillis();
				TurnIsChange = false;
			}
		}

		public void run() {
			// while (true) {
			while (true) {
				try {
					if (loop) {
						Thread.sleep(100);
						timer();
					} else {
						Thread.sleep(1000 * 60 * 60 * 24); // 24�ð� ��������
					}
				} catch (InterruptedException e) {
					break;
				}
				// }
			}
		}

	}

	class ButtonMouseListener implements MouseListener {

		@SuppressWarnings("deprecation")
		@Override
		public void mouseClicked(MouseEvent e) { 
			if(JavaObjClientView.turn!=2) {
			if (e.getSource() == Startbu) {  //���� ����

			
					JavaObjClientView.SendStart();
					
			} else if (e.getSource() == Endbu) { //���� ���� 
				ResetTimer();
				IsStart=false;
				JavaObjClientView.SendInit(); 
			
			}
			else if (e.getSource() == Returnbu) {
				if(JavaObjClientView.turn!=JavaObjClientView.myTurn) //������ �ƴϸ� �۵� �ȵǰ� ��
				JavaObjClientView.SendCancel(); 
			}
			}			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
 
		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
}