import java.util.UUID;


public class HandlerTest {
	public static void main(String[] args) {
		//ÂÖÑ¯Æ÷³õÊ¼»¯
		Looper.perpare();
		
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				System.out.println(Thread.currentThread().getName()+",received:"+msg.toString());
			}
		};
		for (int i = 0; i < 10; i++) {
			new Thread(){
				public void run(){
					while(true){
						Message msg = new Message();
						msg.what = 1;
						synchronized (UUID.class) {
							msg.obj = Thread.currentThread().getName()+",send message:"+UUID.randomUUID().toString();
						}
						System.out.println(msg);
						handler.sendMessage(msg);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		//¿ªÆôÂÖÑ¯
		Looper.loop();
	}
}
