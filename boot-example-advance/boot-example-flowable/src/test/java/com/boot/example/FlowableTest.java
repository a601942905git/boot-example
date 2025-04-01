package com.boot.example;

import org.apache.commons.collections4.CollectionUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lipeng
 * &#064;date 2025/3/31 16:08:26
 */
@SpringBootTest
public class FlowableTest {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Test
    void sub() {
        // 员工提交请假申请
        Map<String, Object> map = new HashMap<>();
        map.put("day", 5);
        map.put("studentUser", "小明");
        // leave为员工请假流程xml文件中的id
        ProcessInstance a1 = runtimeService.startProcessInstanceByKey("leave", map);

        Task task = taskService.createTaskQuery().processInstanceId(a1.getId()).singleResult();
        taskService.complete(task.getId());
    }


    @Test
    void queryLeadTask() {
        // 查询领导分组的任务
        List<Task> teacher = taskService.createTaskQuery().taskCandidateGroup("a").list();
        for (Task task : teacher) {
            // 根据任务id查询当前任务参数
            Map<String, Object> variables = taskService.getVariables(task.getId());
            System.out.println(variables.get("day") + "," + variables.get("studentUser"));
        }
    }

    @Test
    void queryBossTask() {
        // 查询老板分组的任务
        List<Task> teacher = taskService.createTaskQuery().taskCandidateGroup("b").list();
        for (Task task : teacher) {
            // 根据任务id查询当前任务参数
            Map<String, Object> variables = taskService.getVariables(task.getId());
            System.out.println(variables.get("day") + "," + variables.get("studentUser"));
        }
    }

    @Test
    void LeadApprovalTask() {
        // 领导审批
        List<Task> teacherTaskList = taskService.createTaskQuery().taskCandidateGroup("a").list();
        Map<String, Object> teacherMap = new HashMap<>();
        teacherMap.put("outcome", "通过");
        for (Task teacherTask : teacherTaskList) {
            taskService.complete(teacherTask.getId(), teacherMap);
        }
    }

    @Test
    void boosApprovalTask() {
        // 老板审批
        List<Task> teacherTaskList = taskService.createTaskQuery().taskCandidateGroup("b").list();
        Map<String, Object> teacherMap = new HashMap<>();
        teacherMap.put("outcome", "通过");
        for (Task teacherTask : teacherTaskList) {
            taskService.complete(teacherTask.getId(), teacherMap);
        }
    }

    @Test
    void queryHistory() {
        List<ProcessInstance> processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave").orderByStartTime().desc().list();
        if (CollectionUtils.isEmpty(processInstance)) {
            System.out.println("------------------------------------------");
            return;
        }

        // 获取最近的一个流程
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.get(0).getId())
                // 只查询已经完成的活动
                .finished()
                // 按照结束时间排序
                .orderByHistoricActivityInstanceEndTime().desc()
                .list();
        List<String> collect = activities.stream().map(a -> "活动名称:" + a.getActivityName() + ";活动执行时间:" + a.getDurationInMillis() + "毫秒").toList();
        for (String s : collect) {
            System.out.println(s);
        }
    }

    @Test
    void Test() {
        // 发起请假
        Map<String, Object> map = new HashMap<>();
        map.put("day", 5);
        map.put("studentUser", "小明");
        ProcessInstance studentLeave = runtimeService.startProcessInstanceByKey("leave", map);
        System.out.println("processInstanceId:" + studentLeave.getId());
        Task task = taskService.createTaskQuery().processInstanceId(studentLeave.getId()).singleResult();
        taskService.complete(task.getId());

        // 领导审批
        List<Task> teacherTaskList = taskService.createTaskQuery().taskCandidateGroup("a").list();
        Map<String, Object> teacherMap = new HashMap<>();
        teacherMap.put("outcome", "通过");
        for (Task teacherTask : teacherTaskList) {
            sleep();
            taskService.complete(teacherTask.getId(), teacherMap);
        }

        // 老板审批
        List<Task> principalTaskList = taskService.createTaskQuery().taskCandidateGroup("b").list();
        Map<String, Object> principalMap = new HashMap<>();
        principalMap.put("outcome", "通过");
        for (Task principalTask : principalTaskList) {
            sleep();
            taskService.complete(principalTask.getId(), principalMap);
        }

        // 查看历史
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(studentLeave.getId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityName());
        }
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void serviceTaskTest() {
        runtimeService.startProcessInstanceByKey("serviceTaskExample", Collections.singletonMap("email", "a601xxx29gg5@qq.com"));
    }
}
