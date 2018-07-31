/*
the whole util handle threadLocal with shadowcopy,and this util is only for replace
the threadLocals instean of add or remove from threadLocals

and beaware that if there is a threadLocal is not static , the subthread  will get null
for the threadLocal's address is not as same as that in supper thread
**/
public class ConcurrentRequestUtil {
    // 获取当前线程的threadLocal
    public static Object getCurrentThreadLocalMap() {

        return getThreadLocalMap(Thread.currentThread());
    }
    // 使用threadLocal替换当前线程的ThreadLocal
    public static void setCurrentThreadLocalMap(Object threadLocalMap) {

        Field field;
        try {
            field = Thread.class.getDeclaredField("threadLocals");
            field.setAccessible(true);
            field.set(Thread.currentThread(), threadLocalMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    // 获取指定线程的threadLocal
    public static Object getThreadLocalMap(Thread thread) {

        Field field;
        Object map = new Object();
        try {
            field = Thread.class.getDeclaredField("threadLocals");
            field.setAccessible(true);
            return field.get(thread);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
    // 设置threadLocalMap替换到指定线程
    public static void setThreadLocalMap(Thread thread, Object threadLocalMap) {

        Field field;
        try {
            field = Thread.class.getDeclaredField("threadLocals");
            field.setAccessible(true);
            field.set(thread, threadLocalMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
