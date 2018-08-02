package test;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/7/17 0:09 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class RunnableTest {

    public static void main(String[] args) {
        //MyRunnable myRunnable = new MyRunnable();
        //Thread t1 = new Thread(myRunnable);
        //t1.run();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run of Runnable");
            }
        }) {
            public void run() {
                System.out.println("Run of Thread");
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("Run of Runnable");
            }
        }){
            public void run() {
                System.err.println("Run of Thread");
                super.run();
            }
        }.start();
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("线程运行");
        }
    }

}
