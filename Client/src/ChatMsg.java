
// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	private String data;
	public int cancel;
	public int turn;
	public ImageIcon img;
	public int[][] Board;
	public int[][] lastBoard;
	public ChatMsg(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}
	public int[][] getBoard() {
		return Board;
	}
	public void setImg(ImageIcon img) {
		this.img = img;
	}
	public void setBoard(int[][] janggiBoard) {

	Board=janggiBoard;
	
	}
	public void setlastBoard(int[][] janggiBoard) {

	lastBoard=janggiBoard;
	
	}
	public int[][] getlastBoard() {
		return lastBoard;
	}
	
}