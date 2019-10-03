package com.yhw.nc.job.core.provied;

import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yhw
 * @date 2019-09-30
 */
public class NcTaskManager {

    private Map<String, AbstractNcTask> teskRepositry = new HashMap<>();

    public static NcTaskManager getInstance(){
        return Holder.manager;
    }

    public AbstractNcTask getCmdProcess(String name){
        return teskRepositry.get(name);
    }


    public void addNcTasks(List<AbstractNcTask> nctasks){
        if(nctasks != null && !nctasks.isEmpty()){
            for (AbstractNcTask task : nctasks) {
                Assert.isNull(teskRepositry.get(task.getName()),"ncTask name needs to be unique");
                teskRepositry.put(task.getName(),task);
            }
        }
    }

    public List<String> getNames(){
        List<String> result = new ArrayList<>();
        if(teskRepositry == null || teskRepositry.isEmpty()){
            return result;
        }
         result.addAll(teskRepositry.keySet());
        return result;
    }

    public void addNcTask(AbstractNcTask ncTask){
        teskRepositry.put(ncTask.getName(),ncTask);
    }

    static class Holder{
        private static NcTaskManager manager = new NcTaskManager();
    }
}
