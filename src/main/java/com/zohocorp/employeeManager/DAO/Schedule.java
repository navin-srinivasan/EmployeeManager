package com.zohocorp.employeeManager.DAO;

import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.adventnet.taskengine.*;

public class Schedule {

    public Schedule() throws Exception {
        this.scheduleConfiguration();
    }

    public void scheduleConfiguration() throws Exception {
        System.out.println("Schedule initiated");

        //Defining task
        System.out.println("Defining task");
        Persistence pers = (Persistence) BeanUtil.lookup("Persistence");
        DataObject data = pers.constructDataObject();
        Row taskengineRow = new Row(TASKENGINE_TASK.TABLE);
        taskengineRow.set(TASKENGINE_TASK.TASK_NAME, "ActiveUserTask");
        taskengineRow.set(TASKENGINE_TASK.CLASS_NAME, "com.zohocorp.employeeManager.DAO.ActiveUserTask");

        //Creating Schedule
        System.out.println("Creating Schedule");
        Row scheduleRow = new Row(SCHEDULE.TABLE);
        scheduleRow.set(SCHEDULE.SCHEDULE_NAME, "ActiveUserSchedule");

        Row scheduledTaskRow = new Row(SCHEDULED_TASK.TABLE);
        scheduledTaskRow.set(SCHEDULED_TASK.SCHEDULE_ID, scheduleRow.get(SCHEDULE.SCHEDULE_ID));
        scheduledTaskRow.set(SCHEDULED_TASK.TASK_ID, taskengineRow.get(TASKENGINE_TASK.TASK_ID));
        scheduledTaskRow.set(SCHEDULED_TASK.RETRY_HANDLER, "com.adventnet.taskengine.internal.DefaultScheduleRetryHandler");
        Row scheduledTaskRetryRow = new Row(SCHEDULEDTASK_RETRY.TABLE);
        scheduledTaskRetryRow.set(SCHEDULEDTASK_RETRY.SCHEDULE_ID, scheduleRow.get(SCHEDULE.SCHEDULE_ID));
        scheduledTaskRetryRow.set(SCHEDULEDTASK_RETRY.TASK_ID, taskengineRow.get(TASKENGINE_TASK.TASK_ID));
        scheduledTaskRetryRow.set(SCHEDULEDTASK_RETRY.RETRY_COUNT, 5);
        scheduledTaskRetryRow.set(SCHEDULEDTASK_RETRY.RETRY_FACTOR, 2);
        scheduledTaskRetryRow.set(SCHEDULEDTASK_RETRY.RETRY_TIME_PERIOD, 2);
        scheduledTaskRetryRow.set(SCHEDULEDTASK_RETRY.RETRY_UNIT_OF_TIME, "minutes");
        data.addRow(scheduledTaskRetryRow);

        Row periodicRow = new Row(PERIODIC.TABLE);
        periodicRow.set(PERIODIC.SCHEDULE_ID, scheduleRow.get(SCHEDULE.SCHEDULE_ID));
        periodicRow.set(PERIODIC.START_DATE, "2020-05-28 08:30:00.0");
        // java.sql.Timestamp object.
        periodicRow.set(PERIODIC.END_DATE, "2020-05-29 08:30:00.0");
        // java.sql.Timestamp object.
        periodicRow.set(PERIODIC.TIME_PERIOD, 5);
        periodicRow.set(PERIODIC.UNIT_OF_TIME, "minutes");

        data.addRow(taskengineRow);
        data.addRow(scheduleRow);
        data.addRow(periodicRow);
        data.addRow(scheduledTaskRow);
        pers.add(data);

        //Schedular bean mapping
        System.out.println("Schedular bean mapping");
        Scheduler s = (Scheduler) BeanUtil.lookup("Scheduler");
        DataObject taskInputDO = pers.constructDataObject();
        Row taskInputRow = new Row(TASK_INPUT.TABLE);
        taskInputRow.set(TASK_INPUT.EXECUTION_START_TIME,"2020-05-28 08:30:00.0");
        taskInputRow.set(TASK_INPUT.EXECUTION_FINISH_TIME,"2020-05-29 08:30:00.0");
        taskInputDO.addRow(taskInputRow);
        s.scheduleTask("ActiveUserSchedule","ActiveUserTask",taskInputDO);


    }


}
