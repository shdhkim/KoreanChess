public class CheckMove {

	public boolean CheackMalMove(int[][] board, int i, int[] preposition,
			int[] nowposition) {
		if (i == 1 || i == 6) {
			if (checkOne(preposition, nowposition)) {
				
				if (nowposition[0] >= 3 && nowposition[0] <= 5) {
			
					
					if (nowposition[1] >= 0 && nowposition[1] <= 2) {
						return true;
					} else
						return false;
				} else
					return false;
			} else
				return false;
		} else if (i == 8 || i == 13) {
			if (checkOne(preposition, nowposition)) {
				if (nowposition[0] >= 3 && nowposition[0] <= 5) {
					if (nowposition[1] >= 7 && nowposition[1] <= 9) {
						return true;
					} else
						
						return false;
				} else
					return false;
			} else
				return false;

		} else if (i % 7 == 2) {
			if (countSomeDiagonal(board, preposition, nowposition, i) == 0) {
				return true;
			}else if (preposition[0] == nowposition[0]) {
				if (countSomeRow(board, preposition, nowposition) == 0) {
					return true;

				} else
					return false;
			} else if (preposition[1] == nowposition[1]) {
				if (countSomeCol(board, preposition, nowposition) == 0) {
					return true;

				} else
					return false;
			}
			return false;

		} else if (i % 7 == 3) {
			if ((preposition[0] >= 3 && preposition[0] <= 5
					&& preposition[1] >= 0 && preposition[1] <= 2)
					|| (preposition[0] >= 3 && preposition[0] <= 5
							&& preposition[1] >= 7 && preposition[1] <= 9)) {
				if (checkCenter(board, preposition, nowposition)) {
					return true;
				}
			}

			if (preposition[0] == nowposition[0]) {
				if (countSomeRow(board, preposition, nowposition) == 1) {
					return true;

				} else
					return false;
			} else if (preposition[1] == nowposition[1]) {
				if (countSomeCol(board, preposition, nowposition) == 1) {
					return true;

				} else
					return false;
			}
			return false;

		} else if (i % 7 == 4) {
			if (countSomeMa(board, preposition, nowposition) == 0) {
				return true;
			} else
				return false;
		} else if (i % 7 == 5) {
			if (countSomeSang(board, preposition, nowposition) == 0) {
				return true;
			} else
				return false;
		} else if (i == 7) {
			if (checkOne(preposition, nowposition)) {
				if ((preposition[1] - nowposition[1]) <= 0) {
					return true;
				}
			}
			return false;
		} else if (i == 14) {
			if (checkOne(preposition, nowposition)) {
				if ((preposition[1] - nowposition[1]) >= 0) {
					return true;
				}
			}
			return false;
		}

		return true;
	}

	public boolean checkOne(int[] preposition, int[] nowposition) {
		int a, b;
		if ((nowposition[0] >= 3 && nowposition[0] <= 5 && nowposition[1] >= 0 && nowposition[1] <= 2)
				|| (nowposition[0] >= 3 && nowposition[0] <= 5 //대각선 공격이 정해진 칸만 가게 설정
						&& nowposition[1] >= 7 && nowposition[1] <= 9)) {
 			a = preposition[0] - nowposition[0];
			b = preposition[1] - nowposition[1];

			if ((a * a + b * b) == 2) {
			
				
				
 				if((preposition[0]==3&&preposition[1]==1)||(preposition[0]==4&&preposition[1]==2)||(preposition[0]
	         ==5&&preposition[1]==1)||(preposition[0]==4&&preposition[1]==0)||(preposition[0]==3&&preposition[1]==8)||(preposition[0]==4&&preposition[1]==9)||(preposition[0]
	    	         ==5&&preposition[1]==8)||(preposition[0]==4&&preposition[1]==7))
				return false;
				
				
 				else
					
					
					
					
					return true;
				
				
				
				
				
			}
			else {
				a = preposition[0] - nowposition[0];
				b = preposition[1] - nowposition[1];

				if ((a * a + b * b) == 1)
					return true;
				else
					return false;
			}
		} else {
			a = preposition[0] - nowposition[0];
			b = preposition[1] - nowposition[1];

			if ((a * a + b * b) == 1)
				return true;
			else
				return false;
		} 
	}

	public boolean checkCenter(int[][] board, int[] preposition,
			int[] nowposition) {
		int a, b;
		a = preposition[0] - nowposition[0];
		b = preposition[1] - nowposition[1];

		if (nowposition[0] >= 3 && nowposition[0] <= 5 && nowposition[1] >= 0
				&& nowposition[1] <= 2) {
			if (board[4][1] != 0)
				if ((a * a + b * b) == 8)
					return true;
		} else if (nowposition[0] >= 3 && nowposition[0] <= 5
				&& nowposition[1] >= 7 && nowposition[1] <= 9) {
			if (board[4][8] != 0)
				if ((a * a + b * b) == 8)
					return true;
		}
		return false;
	}

	public int countSomeDiagonal(int[][] board, int[] preposition,
			int[] nowposition, int mal) {
		int count = -1;

		if ((preposition[0] >= 3 && preposition[0] <= 5 && preposition[1] >= 0 && preposition[1] <= 2)
				&& (nowposition[0] >= 3 && nowposition[0] <= 5
						&& nowposition[1] >= 0 && nowposition[1] <= 2)) {
			if (preposition[0] == 3 && preposition[1] == 0) {
				for (int i = preposition[0] + 1, j = preposition[1] + 1; i <= 5 ; i++, j++){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			} else if (preposition[0] == 3 && preposition[1] == 2) {
				for (int i = preposition[0] + 1, j = preposition[1] - 1; i <= 5 ; i++, j--){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			} else if (preposition[0] == 5 && preposition[1] == 0) {
				for (int i = preposition[0] - 1, j = preposition[1] + 1; i >= 3 ; i--, j++){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			} else if (preposition[0] == 5 && preposition[1] == 2) {
				for (int i = preposition[0] - 1, j = preposition[1] - 1; i >= 3 ; i--, j--){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			}else if (preposition[0] == 4 && preposition[1] == 1) {
				if (board[nowposition[0]][nowposition[1]] == 0 || board[nowposition[0]][nowposition[1]]/8 != mal/8)
					return count+1;
			} else
				return 1;
			return 1;
		} else if ((preposition[0] >= 3 && preposition[0] <= 5
				&& preposition[1] >= 7 && preposition[1] <= 9)
				&& (nowposition[0] >= 3 && nowposition[0] <= 5
						&& nowposition[1] >= 7 && nowposition[1] <= 9)) {
			if (preposition[0] == 3 && preposition[1] == 7) {
				for (int i = preposition[0] + 1, j = preposition[1] + 1; i <= 5 ; i++, j++){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			} else if (preposition[0] == 3 && preposition[1] == 9) {
				for (int i = preposition[0] + 1, j = preposition[1] - 1; i <= 5 ; i++, j--){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			} else if (preposition[0] == 5 && preposition[1] == 7) {
				for (int i = preposition[0] - 1, j = preposition[1] + 1; i >= 3 ; i--, j++){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			} else if (preposition[0] == 5 && preposition[1] == 9) {
				for (int i = preposition[0] - 1, j = preposition[1] - 1; i >= 3 ; i--, j--){
					if(nowposition[0]==i && nowposition[1]==j)
						return count+1;
					if (board[i][j] != 0)
						count++;
				}
			} else if (preposition[0] == 4 && preposition[1] == 8) {
				if (board[nowposition[0]][nowposition[1]] == 0 || board[nowposition[0]][nowposition[1]]/8 != mal/8)
					return count+1;
			} else
				return 1;
			return 1;
		} else
			return 1;
	}

	public int countSomeRow(int[][] board, int[] preposition, int[] nowposition) {
		int count = 0;
		if (board[preposition[0]][preposition[1]] == 3) {
			if (preposition[1] <= nowposition[1]) {

				for (int i = preposition[1] + 1; i < nowposition[1]; i++) {
					if (board[preposition[0]][i] % 7 == 3) {
						return 0;
					}
				}
			} else {
				for (int i = preposition[1] - 1; i > nowposition[1]; i--) {
					if (board[preposition[0]][i] % 7 == 3) {
						return 0;
					}
				}

			}

		}
		if (preposition[1] <= nowposition[1]) {

			for (int i = preposition[1] + 1; i < nowposition[1]; i++) {
				if (board[preposition[0]][i] != 0) {
					count++;
				}
			}
		} else {
			for (int i = preposition[1] - 1; i > nowposition[1]; i--) {
				if (board[preposition[0]][i] != 0) {
					count++;
				}
			}

		}

		return count;
	}

	public int countSomeCol(int[][] board, int[] preposition, int[] nowposition) {
		int count = 0;
		if (board[preposition[0]][preposition[1]] == 3) {
			if (preposition[0] <= nowposition[0]) {

				for (int i = preposition[0] + 1; i < nowposition[0]; i++) {
					if (board[i][preposition[1]] % 7 == 3) {
						return 0;
					}
				}
			} else {
				for (int i = preposition[0] - 1; i > nowposition[0]; i--) {
					if (board[i][preposition[1]] % 7 == 3) {
						return 0;
					}
				}

			}

		}
		if (preposition[0] <= nowposition[0]) {

			for (int i = preposition[0] + 1; i < nowposition[0]; i++) {
				if (board[i][preposition[1]] != 0) {
					count++;
				}
			}
		} else {
			for (int i = preposition[0] - 1; i > nowposition[0]; i--) {
				if (board[i][preposition[1]] != 0) {
					count++;
				}
			}

		}

		return count;
	}

	public int countSomeMa(int[][] board, int[] preposition, int[] nowposition) {

		if (nowposition[0] - preposition[0] == 1
				&& nowposition[1] - preposition[1] == 2) {
			return board[preposition[0]][preposition[1] + 1];
		} else if (nowposition[0] - preposition[0] == -1
				&& nowposition[1] - preposition[1] == 2) {
			return board[preposition[0]][preposition[1] + 1];
		} else if (nowposition[0] - preposition[0] == -2
				&& nowposition[1] - preposition[1] == 1) {
			return board[preposition[0] - 1][preposition[1]];
		} else if (nowposition[0] - preposition[0] == -2
				&& nowposition[1] - preposition[1] == -1) {
			return board[preposition[0] - 1][preposition[1]];

		} else if (nowposition[0] - preposition[0] == -1
				&& nowposition[1] - preposition[1] == -2) {
			return board[preposition[0]][preposition[1] - 1];
		} else if (nowposition[0] - preposition[0] == 1
				&& nowposition[1] - preposition[1] == -2) {
			return board[preposition[0]][preposition[1] - 1];
		} else if (nowposition[0] - preposition[0] == 2
				&& nowposition[1] - preposition[1] == -1) {
			return board[preposition[0] + 1][preposition[1]];
		} else if (nowposition[0] - preposition[0] == 2
				&& nowposition[1] - preposition[1] == +1) {
			return board[preposition[0] + 1][preposition[1]];
		} else
			return 1;
	}

	public int countSomeSang(int[][] board, int[] preposition, int[] nowposition) {

		if (nowposition[0] - preposition[0] == 2
				&& nowposition[1] - preposition[1] == 3) {
			return board[preposition[0]][preposition[1] + 1]
					+ board[preposition[0] + 1][preposition[1] + 2];
		} else if (nowposition[0] - preposition[0] == -2
				&& nowposition[1] - preposition[1] == 3) {
			return board[preposition[0]][preposition[1] + 1]
					+ board[preposition[0] - 1][preposition[1] + 2];
		} else if (nowposition[0] - preposition[0] == -3
				&& nowposition[1] - preposition[1] == 2) {
			return board[preposition[0] - 1][preposition[1]]
					+ board[preposition[0] - 2][preposition[1] + 1];
		} else if (nowposition[0] - preposition[0] == -3
				&& nowposition[1] - preposition[1] == -2) {
			return board[preposition[0] - 1][preposition[1]]
					+ board[preposition[0] - 2][preposition[1] - 1];
		} else if (nowposition[0] - preposition[0] == -2
				&& nowposition[1] - preposition[1] == -3) {
			return board[preposition[0]][preposition[1] - 1]
					+ board[preposition[0] - 1][preposition[1] - 2];
		} else if (nowposition[0] - preposition[0] == 2
				&& nowposition[1] - preposition[1] == -3) {
			return board[preposition[0]][preposition[1] - 1]
					+ board[preposition[0] + 1][preposition[1] - 2];
		} else if (nowposition[0] - preposition[0] == 3
				&& nowposition[1] - preposition[1] == -2) {
			return board[preposition[0] + 1][preposition[1]]
					+ board[preposition[0] + 2][preposition[1] - 1];
		} else if (nowposition[0] - preposition[0] == 3
				&& nowposition[1] - preposition[1] == 2) {
			return board[preposition[0] + 1][preposition[1]]
					+ board[preposition[0] + 2][preposition[1] + 1];
		} else
			return 1;
	}

}