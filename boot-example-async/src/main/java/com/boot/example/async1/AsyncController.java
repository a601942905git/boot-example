package com.boot.example.async1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * com.boot.example.async1.Async1Controller
 *
 * @author lipeng
 * @date 2018/12/24 上午10:02
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Resource(name = "asyncTaskExecutor")
    private Executor asyncTaskExecutor;

    @RequestMapping("/testAsync1")
    public void testAsync1(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncContext asyncContext = request.startAsync();
        asyncTaskExecutor.execute(() -> {
            // 请求1
            CompletableFuture<List<Integer>> completionStage1 = CompletableFuture.supplyAsync(() -> {
                // 模拟请求
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Integer> numberList = new ArrayList<>();
                numberList.add(1);
                numberList.add(2);
                numberList.add(3);
                numberList.add(4);
                numberList.add(5);
                System.out.println("请求1处理完成");
                return numberList;
            });

            // 请求2
            CompletableFuture<List<Integer>> completionStage2 = CompletableFuture.supplyAsync(() -> {
                // 模拟请求
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Integer> numberList = new ArrayList<>();
                numberList.add(6);
                numberList.add(7);
                numberList.add(8);
                numberList.add(9);
                numberList.add(10);
                System.out.println("请求2处理完成");
                return numberList;
            });

            // 请求3依赖请求2的结果
            completionStage2.thenApplyAsync((request2Result) -> {
                request2Result.add(11);
                request2Result.add(12);
                request2Result.add(13);
                request2Result.add(14);
                request2Result.add(15);
                System.out.println("请求3依赖请求2的结果");
                return request2Result;
            });

            // 组合请求1和请求3的结果
            CompletableFuture<List<Integer>> completionStage =
                    completionStage1.thenCombine(completionStage2, (request1Result, request2Result) -> {
                        request1Result.addAll(request2Result);
                        System.out.println("合并处理结果");
                        return request1Result;
                    });

            completionStage.whenComplete((list, ex) -> {
                try {
                    asyncContext.getResponse().getWriter().write((objectMapper.writeValueAsString(list)));
                    asyncContext.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        System.out.println("主线程执行完毕");
    }

    @RequestMapping("/testCallable")
    public Callable<List<Integer>> testCallable() throws Exception {
        System.out.println("主线程开始执行");
        Callable<List<Integer>> listCallable = () -> {
            Thread.sleep(1000);
            System.out.println("子线程异步处理......");
            return new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
            }};
        };

        System.out.println("主线程结束执行");
        return listCallable;
    }

    @RequestMapping("/testCompletableFuture")
    public CompletableFuture<List<Integer>> testCompletableFuture() {
        System.out.println("主线程开始执行");
        CompletableFuture<List<Integer>> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程异步处理......");
            return new ArrayList<Integer>() {{
                add(100);
                add(200);
                add(300);
                add(400);
                add(500);
            }};
        });
        System.out.println("主线程结束执行");

        return completableFuture;
    }

    @RequestMapping("/testDeferredResult")
    public DeferredResult<List<Integer>> testDeferredResult() {
        System.out.println("主线程开始执行");
        DeferredResult<List<Integer>> deferredResult = new DeferredResult<>();

        asyncTaskExecutor.execute(() -> {
            try {
                Thread.sleep(3000);
                List<Integer> numbers = new ArrayList(){{
                    add(1);
                    add(2);
                    add(3);
                    add(4);
                    add(5);
                    add(4);
                    add(3);
                    add(2);
                    add(1);
                }};
                System.out.println("子线程执行完毕......");
                deferredResult.setResult(numbers);
            } catch (InterruptedException e) {
                deferredResult.setErrorResult(null);
                e.printStackTrace();
            }

        });
        System.out.println("主线程结束执行");
        return deferredResult;
    }

    @RequestMapping("/testWebAsyncTask")
    public WebAsyncTask<List<Integer>> testWebAsyncTask() {
        System.out.println("主线程开始执行");

        Callable<List<Integer>> callable = () -> {
            Thread.sleep(3000);
            List<Integer> numbers = new ArrayList(){{
                add(1000);
                add(2000);
                add(3000);
                add(4000);
                add(5000);
            }};
            System.out.println("子线程执行完毕......");
            return numbers;
        };

        System.out.println("主线程结束执行");
        return new WebAsyncTask<>(callable);
    }

}
