import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MessageQueue {
	
	//通过数组的结构存储Message对象
	Message[] items;
	//代码块枷锁
	/*synchronized (msg) {
		
	}*/
	//入队与出队元素引位置
	int putIndex;
	int takeIndex;
	int count;//计数器
	private Lock lock;
	private Condition notEmpty;
	private Condition notFull;
	
	public MessageQueue() {
		// TODO Auto-generated constructor stub
		this.items = new Message[50];
		this.lock = new ReentrantLock();
		this.notEmpty = lock.newCondition();
		this.notFull = lock.newCondition();
	}
	
	/**
	 * 加入队列（子线程运行）
	 * 生产产品
	 * @param msg
	 * 
	 * */
	public void enqueueMessage(Message msg){
		try {
			lock.lock();
			//消息队列满了，子线程停止发送消息，阻塞
			while(count == items.length){
				try {
					notFull.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			items[putIndex] = msg;
			//循环取值
			putIndex = (++putIndex==items.length)?0:putIndex;
			count++;
			//有新的Message，通知主线程
			notEmpty.signal();
		} finally {
			// TODO: handle exception
			lock.unlock();
		}
	}
	/**
	 * 出队列（主线程运行）
	 * 消费
	 * @param msg
	 * 
	 * */
	public Message next(){
		//消息队列为空，主线程停止轮询，阻塞
		Message msg = null;
		try {
			lock.lock();
			while(count==0){
				try {
					notEmpty.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			msg = items[takeIndex];//取出
			items[takeIndex] = null; //元素设置空
			takeIndex = (++takeIndex==items.length)?0:takeIndex;
			count--;
			
			//使用了一个Message对象，通知子线程，可以继续生产了
			notFull.signal();
		} finally {
			// TODO: handle exception
			lock.unlock();
		}
		
		return msg;
	}
}
