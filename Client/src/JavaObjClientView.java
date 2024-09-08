
// JavaObjClientView.java ObjecStram ��� Client
//�������� ä�� â
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class JavaObjClientView<last> extends JFrame {
	/**
	 * 
	 */
	final static int NULL = 0;

	final static int chang = 1; //�ʷϻ� �� ��ȣ 
	final static int cha = 2;
	final static int pho = 3;
	final static int ma = 4;
	final static int sang = 5;
	final static int sa = 6;
	final static int jol = 7;

	final static int Rchang = 8; //���� �� �� ��ȣ
	final static int Rcha = 9;
	final static int Rpho = 10;
	final static int Rma = 11;
	final static int Rsang = 12;
	final static int Rsa = 13;
	final static int Rjol = 14;
    static int turn=0;
    
	static int myTurn = 0;
	
	boolean start = false;
	boolean janggun = false;
	static Information information;
	CheckStart check;

	JFrame mainFrame;
	JPanel panel = new JPanel();
	static JPanel gameZone = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(icon.getImage(), 0, 0, 653, getHeight(), null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};
	static ImageIcon icon;
	
	CheckMove checkmove = new CheckMove();
	static int[][] janggiBoard;
	static int[][] lastJanggiBoard;

	int[] malIndex, malIndextmp;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInput;
    private static  String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	private Socket socket; // �������
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private static  ObjectOutputStream oos;

	private JLabel lblUserName;
	private JLabel lUserName;
	// private JTextArea textArea;
	private static JTextPane textArea;

	private Frame frame;
	private FileDialog fd;
	private JButton imgBtn;

	/**
	 * Create the frame.
	 */
	public JavaObjClientView(String username, String ip_addr, String port_no) {
		mainFrame = new JFrame(); //â�� �߰��� ����
		mainFrame.setTitle("!- Janggi. F -!");
		mainFrame.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		information = new Information();
		icon = new ImageIcon("images/board.png"); //������ �̹��� ����
		
		check = new CheckStart();
		
		//killSound = new Sound("media/kill.wav", false);
		//endSound = new Sound("media/end.wav", false);
		//moveSound = new Sound("media/move.wav", false);
		//repeatSound = new Sound("media/end.wav", true);

		panel.setLayout(null);
		gameZone.setLayout(null);
		janggiBoard = init();
		gameZone = Locate(janggiBoard, gameZone);
		panel.add(gameZone);
		panel.add(information);
		check.start();

		gameZone.setSize(653, 730);

		mainFrame.setContentPane(panel);
		mainFrame.setBounds(100, 100, 1500, 800/*760*/);
		mainFrame.setResizable(false);
	
		 mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		mainFrame.setVisible(true);
		
		//repeatSound.wakeup();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1000, 150, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 352, 471);
		contentPane.add(scrollPane);

		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setFont(new Font("����ü", Font.PLAIN, 14));
		scrollPane.setViewportView(textArea);

		txtInput = new JTextField();
		txtInput.setBounds(74, 489, 209, 40);
		contentPane.add(txtInput);
		txtInput.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.setFont(new Font("����", Font.PLAIN, 14));
		btnSend.setBounds(295, 489, 69, 40);
		contentPane.add(btnSend);

		lblUserName = new JLabel("Name");
		lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserName.setBackground(Color.WHITE);
		lblUserName.setFont(new Font("����", Font.BOLD, 14));
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(12, 539, 62, 40);
		contentPane.add(lblUserName);
		setVisible(true);

		AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
		UserName = username;
		lblUserName.setText(username);

		lUserName = new JLabel("Name");
		lUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lUserName.setBackground(Color.WHITE);
		lUserName.setFont(new Font("����", Font.BOLD, 14));
		lUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lUserName.setBounds(900, 639, 102, 80);
		panel.add(lUserName);
		

	
	
		lUserName.setText(username);
		imgBtn = new JButton("+");
		imgBtn.setFont(new Font("����", Font.PLAIN, 16));
		imgBtn.setBounds(12, 489, 50, 40);
		contentPane.add(imgBtn);
		
		JButton btnNewButton = new JButton("�� ��");
		btnNewButton.setFont(new Font("����", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
				SendObject(msg);
				System.exit(0);
			}
		});
		btnNewButton.setBounds(295, 539, 69, 40);
		contentPane.add(btnNewButton);

		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
//			is = socket.getInputStream();
//			dis = new DataInputStream(is);
//			os = socket.getOutputStream();
//			dos = new DataOutputStream(os);

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			//SendMessage("/login " + UserName);
			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
			TextSendAction action = new TextSendAction();
			btnSend.addActionListener(action);
			txtInput.addActionListener(action);
			txtInput.requestFocus();
			ImageSendAction action2 = new ImageSendAction();
			imgBtn.addActionListener(action2);

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
		}

	}
	class CheckStart extends Thread {
		int count = 0;

		public void run() {
			// while (true) {
			while (true) {
				if(!information.IsStart && count==0){
					janggiBoard = init();
					gameZone.removeAll();
					gameZone = Locate(janggiBoard, gameZone);
					gameZone.repaint();
					myTurn = 0;
					information.Player[0].setSelected(true);
					information.Player[1].setSelected(false);
					count++;
				}else if(information.IsStart && count != 0){
					count = 0;
				}

			}
			// }
		}
	}

	public class mal extends JButton {
		int x;
		int y;
		ImageIcon icon;

		public mal(int i, int j, String imagePath) {
			setSize(30, 30); //��⸻ �̹��� ũ��
			setLocation((i * 69 + 33), (j * 70 + 33)); //�� ��ġ�� ĭ�� �µ��� ����
			icon = new ImageIcon(imagePath);

			addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					if (information.IsStart) {// �����ߴٸ� 
					

						JButton btn = (JButton) e.getSource();
						btn.setSelected(false);
						x = e.getXOnScreen() - mainFrame.getX();
						y = e.getYOnScreen() - mainFrame.getY() - 30;
						System.out.println(x + "  " + y);
						malIndex = getIndex(x, y);
						System.out.println(malIndex[0] + "  " + malIndex[1]);
						System.out
								.println(janggiBoard[malIndextmp[0]][malIndextmp[1]]);
						System.out.println(myTurn);
						if (janggiBoard[malIndextmp[0]][malIndextmp[1]] / 8 == myTurn&&janggiBoard[malIndextmp[0]][malIndextmp[1]] / 8 == turn) {
							if (malIndex[0] == malIndextmp[0]
									&& malIndextmp[1] == malIndex[1])
								;
							else if (checkmove
									.CheackMalMove(
											janggiBoard,
											janggiBoard[malIndextmp[0]][malIndextmp[1]],
											malIndextmp, malIndex)) {
								if (janggiBoard[malIndex[0]][malIndex[1]] == NULL) {
									lastJanggiBoard=new int[9][10];
									for (int i = 0; i < 9; i++) {
										for (int j = 0; j < 10; j++) {
											
											lastJanggiBoard[i][j]=janggiBoard[i][j];
										}
									}
									janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]];
									janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
									//moveSound.wakeup();
								
									checkJang();
									ChatMsg obcm = new ChatMsg(UserName, "600", "PUT"); //���� �ű�� ������ ��ü�� ����
									obcm.setlastBoard(lastJanggiBoard);
									obcm.setBoard(janggiBoard);
									SendObject(obcm);
								} else if (janggiBoard[malIndex[0]][malIndex[1]] / 8 == janggiBoard[malIndextmp[0]][malIndextmp[1]] / 8)
									;
								else if (janggiBoard[malIndex[0]][malIndex[1]] % 7 == 3
										&& janggiBoard[malIndextmp[0]][malIndextmp[1]] % 7 == 3)
									;
								else {

									if (myTurn == 0
											&& janggiBoard[malIndex[0]][malIndex[1]] == 8) {
										janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]];
										janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
										start = false;
										//endSound.wakeup();
										JOptionPane.showMessageDialog(null, "������ �����ϴ�!!", "��������",
												JOptionPane.WARNING_MESSAGE);
										
										information.IsStart = false;
										information.ResetTimer(); //Ÿ�̸� �ʱ�ȭ
										information.IsStart=false;
										information.PlayerPanel[0].removeAll();
										information.PlayerPanel[1].removeAll();	
										janggiBoard = JavaObjClientView.this.init();
                                    ChatMsg obcm = new ChatMsg(UserName, "601", "INIT"); // ������ ����Ǹ� ������ ��ü�� ����
										
										obcm.setBoard(janggiBoard);
										SendObject(obcm);
										
									} else if (myTurn == 1
											&& janggiBoard[malIndex[0]][malIndex[1]] == 1) {
										janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]];
										janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
										start = false;
										//endSound.wakeup();
								
										JOptionPane.showMessageDialog(null, "������ �����ϴ�!!", "��������",
												JOptionPane.WARNING_MESSAGE);
										information.IsStart = false;
										information.ResetTimer();   //Ÿ�̸� �ʱ�ȭ
										information.IsStart=false;
										information.PlayerPanel[0].removeAll();
										information.PlayerPanel[1].removeAll();	
										janggiBoard = JavaObjClientView.this.init();
										ChatMsg obcm = new ChatMsg(UserName, "601", "INIT"); // ������ ����Ǹ� ������ ��ü�� ����
										
										obcm.setBoard(janggiBoard);
										SendObject(obcm);
									} else {
										for (int i = 0; i < 9; i++) {
											for (int j = 0; j < 10; j++) {
												
												lastJanggiBoard[i][j]=janggiBoard[i][j]; //lastJanggiBoard=janggiBoard�� �ϸ� �迭�� �����ؼ� �迭�� �ٲٸ� ������ ���� �̷������� ������
											}
										}
										
										for(int i=1;i<=14;i++){
										if(	janggiBoard[malIndex[0]][malIndex[1]] ==i) { //�����Ա� ���
											
											if(janggiBoard[malIndex[0]][malIndex[1]]<=7) 
										janggiBoard[malIndex[0]][malIndex[1]] = i+7;
								
										
											else if(janggiBoard[malIndex[0]][malIndex[1]]<=14)
											janggiBoard[malIndex[0]][malIndex[1]] = i-7;
											break;
										}
										}
										//killSound.wakeup();
										
										checkJang();
										ChatMsg obcm = new ChatMsg(UserName, "600", "PUT");
										obcm.setlastBoard(lastJanggiBoard);
										obcm.setBoard(janggiBoard);
										SendObject(obcm);
									
									}
								}
							}
							
						}

						btn.setLocation(checkPlace(x, y));
						gameZone.removeAll();
						gameZone = Locate(janggiBoard, gameZone);
						gameZone.repaint();
						Rint(janggiBoard);
						System.out.println("");
						
					}
				
				}
			
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					JButton btn = (JButton) e.getSource();
					btn.setSelected(false);
					x = e.getXOnScreen() - mainFrame.getX();
					y = e.getYOnScreen() - mainFrame.getY() - 30;
					malIndextmp = getIndex(x, y);
					System.out.println(malIndextmp[0] + "  " + malIndextmp[1]);
					

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});

			addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					if (information.IsStart) {
						JButton btn = (JButton) e.getSource();
						x = e.getXOnScreen() - mainFrame.getX();
						y = e.getYOnScreen() - mainFrame.getY();
						if (x < 650 && x > 10 && y < 770 && y > 10)
							btn.setLocation(x - 25, y - 60);
					}

				}

				@Override
				public void mouseMoved(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
		}

		public void paintComponent(Graphics g) {
			g.drawImage(icon.getImage(), 0, 0, 30, 30, null); //��⸻ �̹��� ũ��
			setOpaque(false);
		}
	}

	public int[] getIndex(int ox, int oy) {
		int[] Index = new int[2];
		Point tmp = new Point();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if ((tmp.x - ox) * (tmp.x - ox) + (tmp.y - oy) * (tmp.y - oy) > (((i + 1) * 72) - ox)
						* (((i + 1) * 72) - ox)
						+ (((j + 1) * 72) - oy)
						* (((j + 1) * 72) - oy)) {
					tmp.setLocation(i * 72 + 20, j * 72 + 20);
					Index[0] = i;
					Index[1] = j;
				}
			}
		}

		return Index;
	}

	public Point checkPlace(int ox, int oy) {
		Point Place = new Point();
		Point tmp = new Point();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if ((tmp.x - ox) * (tmp.x - ox) + (tmp.y - oy) * (tmp.y - oy) > (((i + 1) * 72) - ox)
						* (((i + 1) * 72) - ox)
						+ (((j + 1) * 72) - oy)
						* (((j + 1) * 72) - oy)) {
					tmp.setLocation(i * 72 + 20, j * 72 + 20);
				}
			}
		}
		Place.setLocation(tmp.getLocation());

		return Place;
	}

	public void Rint(int[][] janggiBoard) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(" " + janggiBoard[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public static  int[][] init() {

		int[][] Janggipan = {
				{ cha, NULL, NULL, jol, NULL, NULL, Rjol, NULL, NULL, Rcha },
				{ sang, NULL, pho, NULL, NULL, NULL, NULL, Rpho, NULL, Rma },
				{ ma, NULL, NULL, jol, NULL, NULL, Rjol, NULL, NULL, Rsang },
				{ sa, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, Rsa },
				{ NULL, chang, NULL, jol, NULL, NULL, Rjol, NULL, Rchang, NULL },
				{ sa, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, Rsa },
				{ sang, NULL, NULL, jol, NULL, NULL, Rjol, NULL, NULL, Rsang },
				{ ma, NULL, pho, NULL, NULL, NULL, NULL, Rpho, NULL, Rma },
				{ cha, NULL, NULL, jol, NULL, NULL, Rjol, NULL, NULL, Rcha }

		};

		return Janggipan;
	}

	public  JPanel Locate(int[][] board, JPanel Janggi) {
//�̹��� ����
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if (board[i][j] == chang) {
					mal Pchang = new mal(i, j, "images/chang.emf");
					Pchang.setBackground(Color.GREEN);
					Pchang.setName("images/chang.emf");
					
					Pchang.setBorderPainted(false);
					Janggi.add(Pchang);
				} else if (board[i][j] == cha) {
					mal Pcha = new mal(i, j, "images/cha.emf");
					Pcha.setBackground(Color.GREEN);
					Pcha.setName("images/cha.emf");
					Pcha.setBorderPainted(false);
					Janggi.add(Pcha);
				} else if (board[i][j] == pho) {
					mal Ppho = new mal(i, j, "images/po.emf");
					Ppho.setBackground(Color.GREEN);
					Ppho.setName("images/po.emf");
					Ppho.setBorderPainted(false);
					Janggi.add(Ppho);
				} else if (board[i][j] == ma) {
					mal Pma = new mal(i, j, "images/ma.emf");
					Pma.setBackground(Color.GREEN);
					Pma.setName("images/ma.emf");
					Pma.setBorderPainted(false);
					Janggi.add(Pma);
				} else if (board[i][j] == sang) {
					mal Psang = new mal(i, j, "images/sang.emf");
					Psang.setBackground(Color.GREEN);
					Psang.setName("images/sang.emf");
					Psang.setBorderPainted(false);
					Janggi.add(Psang);
				} else if (board[i][j] == sa) {
					mal Psa = new mal(i, j, "images/sa.emf");
					Psa.setBackground(Color.GREEN);
					Psa.setName("images/sa.emf");
					Psa.setBorderPainted(false);
					Janggi.add(Psa);
				} else if (board[i][j] == jol) {
					mal Pjol = new mal(i, j, "images/jol.emf");
					Pjol.setBackground(Color.GREEN);
					Pjol.setName("images/chojol.emf");
					Pjol.setBorderPainted(false);
					Janggi.add(Pjol);
				}

				else if (board[i][j] == Rchang) {
					mal Rchang = new mal(i, j, "images/rchang.emf");
					Rchang.setBackground(Color.RED);
					Rchang.setName("images/rchang.emf");
					Rchang.setBorderPainted(false);
					Janggi.add(Rchang);
				} else if (board[i][j] == Rcha) {
					mal Rcha = new mal(i, j, "images/rcha.emf");
					Rcha.setBackground(Color.RED);
					Rcha.setName("images/rcha.emf");
					Rcha.setBorderPainted(false);
					Janggi.add(Rcha);
				} else if (board[i][j] == Rpho) {
					mal Rpho = new mal(i, j, "images/rpo.emf");
					Rpho.setBackground(Color.RED);
					Rpho.setName("images/rpo.emf");
					Rpho.setBorderPainted(false);
					Janggi.add(Rpho);
				} else if (board[i][j] == Rma) {
					mal Rma = new mal(i, j, "images/rma.emf");
					Rma.setBackground(Color.RED);
					Rma.setName("images/rma.emf");
					Rma.setBorderPainted(false);
					Janggi.add(Rma);
				} else if (board[i][j] == Rsang) {
					mal Rsang = new mal(i, j, "images/rsang.emf");
					Rsang.setBackground(Color.RED);
					Rsang.setName("images/rsang.emf");
					Rsang.setBorderPainted(false);
					Janggi.add(Rsang);
				} else if (board[i][j] == Rsa) {
					mal Rsa = new mal(i, j, "images/rsa.emf");
					Rsa.setBackground(Color.RED);
					Rsa.setName("images/rsa.emf");
					Rsa.setBorderPainted(false);
					Janggi.add(Rsa);
				} else if (board[i][j] == Rjol) {
					mal Rjol = new mal(i, j, "images/rjol.emf");
					Rjol.setBackground(Color.RED);
					Rjol.setName("images/rjol.emf");
					Rjol.setBorderPainted(false);
					Janggi.add(Rjol);
				}
			}
		}

		return Janggi;
	}

	public void checkJang() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if (janggiBoard[i][j] == 8) {
					malIndex[0] = i;
					malIndex[1] = j;
					janggun = true;
				}
				if (janggun)
					break;
			}
			if (janggun)
				break;
		}
		janggun = false;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if (janggiBoard[i][j] / 8 == 0 && janggiBoard[i][j] != 0) {
					malIndextmp[0] = i;
					malIndextmp[1] = j;
					if (checkmove.CheackMalMove(janggiBoard,
							janggiBoard[i][j], malIndextmp, malIndex)) {
						JOptionPane.showMessageDialog(null, "�屺�Դϴ�!!", "�屺",
								JOptionPane.WARNING_MESSAGE); //���� ȭ�鿡 �޽����� ��
						janggun = true;
					}
				}
				if (janggun)
					break;
			}
			if (janggun)
				break;
		}
		janggun = false;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if (janggiBoard[i][j] == 1) {
					malIndex[0] = i;
					malIndex[1] = j;
					janggun = true;
				}
				if (janggun)
					break;
			}
			if (janggun)
				break;
		}
		janggun = false;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if (janggiBoard[i][j] / 8 == 1) {
					malIndextmp[0] = i;
					malIndextmp[1] = j;
					if (checkmove.CheackMalMove(janggiBoard,
							janggiBoard[i][j], malIndextmp, malIndex)) {
						JOptionPane.showMessageDialog(null, "�屺�Դϴ�!!", "�屺",
								JOptionPane.WARNING_MESSAGE); //���� ȭ�鿡 �޽����� ��
						janggun = true;
					}
				}
				if (janggun)
					break;
			}
			if (janggun)
				break;
		}
		janggun = false;


	

	}

	public static void changeTurn() {
		if (myTurn == 0) {
			myTurn = 1;
			information.setTurnIsChangeToTrue();
		} else {
			myTurn = 0;
			information.setTurnIsChangeToTrue();
		}
	}
	// Server Message�� �����ؼ� ȭ�鿡 ǥ��
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					// String msg = dis.readUTF();
//					byte[] b = new byte[BUF_LEN];
//					int ret;
//					ret = dis.read(b);
//					if (ret < 0) {
//						AppendText("dis.read() < 0 error");
//						try {
//							dos.close();
//							dis.close();
//							socket.close();
//							break;
//						} catch (Exception ee) {
//							break;
//						}// catch�� ��
//					}
//					String	msg = new String(b, "euc-kr");
//					msg = msg.trim(); // �յ� blank NULL, \n ��� ����

					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
					} else
						continue;
					switch (cm.getCode()) {
					case "100": // chat message
						
						
						turn=2;
					
						//turn�� 2�� 3��° �����ں��ʹ� ������ �Ҽ� �ְ� ������ �÷��� �� �� ����.
						break;
					case "200": // chat message
						if(cm.getData().equals("Y")) {
                            if(cm.cancel==1) {
                            	for (int i = 0; i < 9; i++) {
									for (int j = 0; j < 10; j++) {
										
										janggiBoard[i][j]=lastJanggiBoard[i][j];
									}
								}
            					
        						if (myTurn == 0) {
        							myTurn = 1;
        							information.setTurnIsChangeToTrue();
        						} else {
        							myTurn = 0;
        							information.setTurnIsChangeToTrue();
        						}
        						gameZone.removeAll();
        						gameZone = Locate(janggiBoard, gameZone);
        						
        						gameZone.repaint();
                            	
                            	
                            	
                            	continue;
                            }
                            if(cm.turn==1)turn=1;
                            else if(cm.turn==0)turn=0;
							if (information.first) {// ó�� ���� ��������(ó�� �����Ҷ�)
								
								information.loop = true;
								information.oo = System.currentTimeMillis();//���� �ð��� ���Ѵ�.
								information.oo1 = System.currentTimeMillis();//���� �ð��� ����
								information.PlayTimer.start();
								information.RemainTimer.start();
								information.first = false;
							
							} else {// ������ ������ ���� ��������(ó�� ������ �ƴҶ�)
								
								information.oo = System.currentTimeMillis();
								information.oo1 = System.currentTimeMillis();
								information.PlayTimer.resume();
								information.RemainTimer.resume();
								
							}
							information.IsStart=true;
							information.HansuBack.setVisible(true);
							information.Startbu.setVisible(false);
						continue;	
						
						}
						AppendText(msg);
					
						break;
					case "300": // Image ÷��
						AppendText("[" + cm.getId() + "]");
						AppendImage(cm.img);
						break;
					case "600":
					lastJanggiBoard=(cm.getlastBoard());
						janggiBoard=(cm.getBoard());
					
						if (myTurn == 0) {
							myTurn = 1;
							information.setTurnIsChangeToTrue();
						} else {
							myTurn = 0;
							information.setTurnIsChangeToTrue();
						}
						gameZone.removeAll();
						gameZone = Locate(janggiBoard, gameZone);
						
						gameZone.repaint();
						break;
					case "601":
						information.ResetTimer();
						information.IsStart=false;
						janggiBoard=(cm.getBoard());
						gameZone.removeAll();
						gameZone = Locate(janggiBoard, gameZone);
						
						gameZone.repaint();
						break;
					case "602": // chat message
						AppendText(msg);
						
						break;
					case "603": // chat message
						AppendText(msg);
						break;
				
					}
				
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch�� ��
				} // �ٱ� catch����

			}
		}
	}

	// keyboard enter key ġ�� ������ ����
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button�� �����ų� �޽��� �Է��ϰ� Enter key ġ��
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				SendMessage(msg);
				txtInput.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
				txtInput.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��
				if (msg.contains("/exit")) // ���� ó��
					System.exit(0);
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
			if (e.getSource() == imgBtn) {
				frame = new Frame("�̹���÷��");
				fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
				// frame.setVisible(true);
				// fd.setDirectory(".\\");
				fd.setVisible(true);
				//System.out.println(fd.getDirectory() + fd.getFile());
				ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
				ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
				obcm.setImg(img);
				SendObject(obcm);
			}
		}
	}

	ImageIcon icon1 = new ImageIcon("src/icon1.jpg");

	public void AppendIcon(ImageIcon icon) {
		int len = textArea.getDocument().getLength();
		// ������ �̵�
		textArea.setCaretPosition(len);
		textArea.insertIcon(icon);
	}

	// ȭ�鿡 ���
	public static void AppendText(String msg) {
		// textArea.append(msg + "\n");
		//AppendIcon(icon1);
		msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
		int len = textArea.getDocument().getLength();
		// ������ �̵�
		textArea.setCaretPosition(len);
		textArea.replaceSelection(msg + "\n");
	}

	public void AppendImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
		if (width > 200 || height > 200) {
			if (width > height) { // ���� ����
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // ���� ����
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			textArea.insertIcon(new_icon);
		} else
			textArea.insertIcon(ori_icon);
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
		// ImageViewAction viewaction = new ImageViewAction();
		// new_icon.addActionListener(viewaction); // ����Ŭ������ �׼� �����ʸ� ��ӹ��� Ŭ������
	}

	// Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server���� network���� ����
	public void SendMessage(String msg) {
		try {
			// dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
			ChatMsg obcm = new ChatMsg(UserName, "200", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
			// AppendText("dos.write() error");
			AppendText("oos.writeObject() error");
			try {
//				dos.close();
//				dis.close();
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}
	public static void SendTurn(){
	ChatMsg obcm = new ChatMsg(UserName, "600", "PUT");
	lastJanggiBoard=new int[9][10];
	for (int i = 0; i < 9; i++) {
		for (int j = 0; j < 10; j++) {
			
			lastJanggiBoard[i][j]=janggiBoard[i][j];
		}
	}
	obcm.setBoard(lastJanggiBoard);
	obcm.setBoard(janggiBoard);
	
	SendObject(obcm);
	}
	public static void SendInit() {
		                                   
	janggiBoard = init();

    ChatMsg obcm = new ChatMsg(UserName, "601", "INIT");
		
		obcm.setBoard(janggiBoard);
		SendObject(obcm);
	}
	public static void SendStart(){         //���� ����
		 ChatMsg obcm = new ChatMsg(UserName, "602", "START");
			
			SendObject(obcm);
		  
		
	} 
	public static void SendCancel(){   //������
		ChatMsg obcm = new ChatMsg(UserName, "603", "CANCEL");
		
		
		SendObject(obcm);
		}
	public static   void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("�޼��� �۽� ����!!\n");
			AppendText("SendObject Error");
		}
	}
}
