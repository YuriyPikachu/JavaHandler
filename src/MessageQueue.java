import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MessageQueue {
	
	//ͨ������Ľṹ�洢Message����
	Message[] items;
	//��������
	/*synchronized (msg) {
		
	}*/
	//��������Ԫ����λ��
	int putIndex;
	int takeIndex;
	int count;//������
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
	 * ������У����߳����У�
	 * ������Ʒ
	 * @param msg
	 * 
	 * */
	public void enqueueMessage(Message msg){
		try {
			lock.lock();
			//��Ϣ�������ˣ����߳�ֹͣ������Ϣ������
			while(count == items.length){
				try {
					notFull.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			items[putIndex] = msg;
			//ѭ��ȡֵ
			putIndex = (++putIndex==items.length)?0:putIndex;
			count++;
			//���µ�Message��֪ͨ���߳�
			notEmpty.signal();
		} finally {
			// TODO: handle exception
			lock.unlock();
		}
	}
	/**
	 * �����У����߳����У�
	 * ����
	 * @param msg
	 * 
	 * */
	public Message next(){
		//��Ϣ����Ϊ�գ����߳�ֹͣ��ѯ������
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
			msg = items[takeIndex];//ȡ��
			items[takeIndex] = null; //Ԫ�����ÿ�
			takeIndex = (++takeIndex==items.length)?0:takeIndex;
			count--;
			
			//ʹ����һ��Message����֪ͨ���̣߳����Լ���������
			notFull.signal();
		} finally {
			// TODO: handle exception
			lock.unlock();
		}
		
		return msg;
	}
}
