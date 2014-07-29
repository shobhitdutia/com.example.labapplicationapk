
public class Item extends Thread{
	String type;
	int id;
	//static Supplier s=new Supplier();
	static Semaphore s=new Semaphore();
	public Item(String type, int id) {
		this.type=type;
		this.id=id;
	}

	public void run() {
		//s.getItemsOnTable();
		while(true) {
			System.out.println("Maker "+id+" acquiring lock");
			if(s.acquire(type));
			System.out.println("Maker "+id+" produced Hot Dog");
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.release();
		}
	}
	
	public static void main(String[] args) {
		triggerSupplier();
		Item one=new Item("sausage", 1);
		Item two=new Item("bun", 2);
		Item three=new Item("mustard", 3);
		one.start();
		two.start();
		three.start();
		
	}

	private static void triggerSupplier() {
		new Supplier();
	}
}
