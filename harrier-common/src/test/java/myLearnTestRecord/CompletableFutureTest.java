package myLearnTestRecord;

import cn.spdb.harrier.common.utils.NameThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableFutureTest {
    static class Task implements Supplier{
        private String taskNo;
        private String ret;

        public Task(int i) {
            this.taskNo= String.valueOf(i);
        }

        @Override
        public Object get() {
            System.out.println("线程："+Thread.currentThread().getName()+"执行作业"+taskNo+"开始执行");
            int time = ThreadLocalRandom.current().nextInt(5);
            try {
                TimeUnit.SECONDS.sleep(time);
                ret="线程："+Thread.currentThread().getName()+"执行作业"+taskNo+"执行完成!耗时:"+time+"\n";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ret;
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),new NameThreadFactory(CompletableFutureTest.class.getSimpleName()));

        List<CompletableFuture> Tasks=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tasks.add(CompletableFuture.supplyAsync(new Task(i),exec));
        }
        //任一任务完成结束=》缺陷两种停的方式都不能立即停下所有任务，但第一种比第二种较为有效
//        CompletableFuture.anyOf(Tasks.toArray(new CompletableFuture[0])).thenAccept(result->{
//            System.out.println("第一个跑完："+result);
//            exec.shutdownNow();//立即杀
////            Tasks.forEach(task->{//一个一个停
////                task.cancel(true);
////            });
//        });

        //打印所有结果
        CompletableFuture.allOf(Tasks.toArray(new CompletableFuture[0])).thenAccept(result->{
            System.out.println("所有任务结束！");
            StringBuilder resultStr = new StringBuilder("所有任务结果汇总：\n");
            Tasks.stream().forEach(task->{
                try {
                    resultStr.append(task.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            });
            System.out.println(resultStr);
            exec.shutdown();
        });

    }


}
