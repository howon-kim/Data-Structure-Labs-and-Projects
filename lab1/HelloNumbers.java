public class HelloNumbers {
	public static void main(String[] args) {
		int x, y;
		x = y = 0;
		while (x < 10){
			y = y + x;
			x = x + 1;
			System.out.print(y + " ");
		}
	}
}

