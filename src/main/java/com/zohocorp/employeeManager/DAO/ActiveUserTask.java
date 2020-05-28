package com.zohocorp.employeeManager.DAO;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.*;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.*;
import com.adventnet.taskengine.Task;
import com.adventnet.taskengine.TaskContext;
import com.adventnet.taskengine.TaskExecutionException;

import java.time.LocalDateTime;

public class ActiveUserTask implements Task {

    @Override
    public void executeTask(TaskContext taskContext) throws TaskExecutionException {
        System.out.println("Task initiated");
        DataObject dataObject = new WritableDataObject();
        Row row = new Row("EM_Scheduler");
        row.set("EM_Scheduler_Time", ""+LocalDateTime.now());
        try {
            dataObject.addRow(row);
            Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            persistence.add(dataObject);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void stopTask() throws TaskExecutionException {
    }
}
